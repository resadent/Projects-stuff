function rot13(str) {
  let alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";     
  let outStr='';
  let ciph=13,newCharPos=0;
  for (let i=0;i<str.length;i++){
    let newChar=str.charAt(i);
      if (newCharPos=alphabet.indexOf(newChar)>=0){  
        newCharPos=alphabet.indexOf(newChar);
        newChar=alphabet.charAt(
          Math.abs((newCharPos+ciph)) % alphabet.length
        );
      }
      outStr+=newChar;
      //console.log(newChar);
    }

  return outStr;
}

console.log(rot13("SERR PBQR PNZC"));
console.log(rot13("SERR YBIR?"));
console.log(rot13("GUR DHVPX OEBJA SBK WHZCF BIRE GUR YNML QBT.") );
// A <-> N