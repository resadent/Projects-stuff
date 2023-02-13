function telephoneCheck(str) {
  let reg=[];
  reg[0]=/^\d{3}-\d{3}-\d{3}$/
  reg[1]=/^\(\d{3}\)\d{3}-\d{3}$/
  reg[2]=/^\(\d{3}\)\s\d{3}-\d{4}$/
  reg[3]=/^\d{3}\s\d{3}\s\d{3}$/
  reg[4]=/^\d{10}$/
  reg[5]=/^1\s\d{3}\s\d{3}\s\d{4}$/
  reg[6]=/^1\s\(\d{3}\)\s\d{3}-\d{4}$/
  reg[7]=/^\d{3}-\d{3}-\d{4}$/
  reg[8]=/^1\s\d{3}-\d{3}-\d{4}$/
  reg[9]=/^\(\d{3}\)\d{3}-\d{4}$/
  reg[10]=/^1\(\d{3}\)\d{3}-\d{4}$/
  let found=false;
  for (let i=0;i<reg.length;i++){
    found ||= reg[i].test(str);
    //console.log('this has been',found)
  }
  return found;
}

//console.log(telephoneCheck("555-555-5555"));
let num='555-555-5555'
let rege=/^\d{3}-\d{3}-\d{4}$/
//console.log(rege.test(num))
let reg=[];
reg.push(/^\d{3}-\d{3}-\d{4}$/);
reg[1]=/^\d{3}-\d{3}-\d{4}$/