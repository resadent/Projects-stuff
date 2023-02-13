function convertToRoman(num) {
  let chart={'M':1000,'CM':900,'D':500,'CD':400,'C':100,'XC':90,'L':50,'XL':40,'X':10,'IX':9,'V':5,'IV':4,'I':1};
  let values=Object.keys(chart);
  let out='';
  let aux=0;
  while (aux<num){
    let found=false;
    for (let i = 0;i<values.length && !found;i++){
      if (aux + chart[values[i]] <= num){
        found=true;
        aux+=chart[values[i]];
        out+=values[i];
      }
    }
  }
 return out;
}

console.log(convertToRoman(36));