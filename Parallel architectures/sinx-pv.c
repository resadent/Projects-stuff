#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <assert.h>
#include <math.h>
#include <emmintrin.h>

double gettime(void)
{
    struct timeval tv;
    gettimeofday(&tv,NULL);
    return tv.tv_sec + 1e-6*tv.tv_usec;
}

/*******************************************************************************
	* Sin(x) Taylor Series  
	********************************************************************************
	*           oo  (-1)^n                -1         1           -1
	* sin(x) = Sum ------- x^(2n+1) = x + -- x^3 + ----- x^5 + ------- x^7 + ...
	*          n=0 (2n+1)!                 6       5*4*6       7*6*120
********************************************************************************/
void sinx (int N, int terms, float *x, float *y)
{
	int i,j,k;
	
	__m128 origx, value, numer, denom, aux;
	
	#pragma omp for private(i,j)
	for (i=0; i<N; i+=4)
	{
		origx = _mm_load_ps(&x[i]);
		value = origx;
		numer = _mm_mul_ps(origx,_mm_mul_ps(origx,origx));
		int sign = -1;
		denom = _mm_set1_ps((float) 6);
		
		for (j=1; j<=terms; j++)
		{
			aux = _mm_div_ps(numer,denom);
			aux = _mm_mul_ps(_mm_set1_ps((float) sign), aux);
			value = _mm_add_ps(value, aux);
			numer = _mm_mul_ps(numer,_mm_mul_ps(origx, origx));
			denom = _mm_mul_ps(denom,_mm_set1_ps((float)(2*j+2)*(2*j+3)));
			sign *= -1;
		}
		
		_mm_store_ps(&y[i], value);
	}
}

int main(int argc, char *argv[])
{
	int N, terms, i, delta, plot;
	float *x, *y;
	double dtime;
	FILE *res;
	
	if (argc == 4){
		N = atoi(argv[1]);
		terms = atoi(argv[2]);
		plot = 1;
		} else if (argc == 3){
		N = atoi(argv[1]);
		terms = atoi(argv[2]);
		plot = 0;
		} else{
		printf("*** Use: %s N terms [-p] ***\n", argv[0]);
		printf("*    Ex: %s 131072 12 -p   \n", argv[0]);
		printf("*    -p: plots results     \n");
		return 0;
	}
	printf("Using N=%d and terms=%d (%s).\n",N,terms,plot?"Plot":"No plot");
	
	x = (float *) malloc(N*sizeof(float));
	assert(x);
	y = (float *) malloc(N*sizeof(float));
	assert(y);
	
	//X from -1.5*pi to 1.5*pi
	for (i=0; i<N; i++)
	x[i] = -1.5*M_PI + (i*3*M_PI)/N;
	
	dtime = gettime();
	
	#pragma omp parallel
	sinx(N,terms,x,y);
	
	dtime = gettime() - dtime;
	
	printf("Exec time: %9.5f sec.\n",dtime);
	
	if(plot) {
		//Openning the file to write the results (x,y), for gnuplot input
		res = fopen("sinx.txt","w");
		assert(res);
		delta=1;
		//For plotting we do not need too many points
		if(N > 1000) delta = N/1000;
		for (i=0; i<N; i+=delta)
		fprintf(res, "%2.5f %2.5f\n", x[i], y[i]);
		fclose(res);
		//Calling gnuplot from command line
		//Using pause for the window not to close right away (close with a click)
		assert(system("gnuplot -e \"plot 'sinx.txt' with lines; pause mouse any\"") != -1);
		} else {
		printf("(x[0],y[0]) = (%1.4f,%1.4f); (x[N-1],y[N-1]) = (%1.4f,%1.4f)\n",
		x[0], y[0], x[N-1], y[N-1]);
	}
	free(x);
	free(y);
	
	return 0;
}
