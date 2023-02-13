function palindrome(str) {
  let regex=/[A-Za-z0-9]+/g;
  let matches=str.match(regex);
  let newStr=''
  for (let i=0;i<matches.length;i++){
    for (let j=0;j<matches[i].length;j++){
      newStr+=matches[i].charAt(j).toLowerCase();
    }
  }
  let mid=parseInt(newStr.length/2);
  let found=true;
  for (let i=0;i<mid && found;i++){
    if (newStr.charAt(i)!=newStr.charAt(newStr.length-i-1)){
      found=false;
    }
  }
  console.log(mid)
  return found;
}

palindrome("eye");
palindrome("My age is 0, 0 si ega ym.");
palindrome("0_0 (: /-\ :) 0-0");
palindrome("A man, a plan, a canal. Panama");

/*
calcular num de chars
comparar caracteres de 0 a mid y de end a mid

*/