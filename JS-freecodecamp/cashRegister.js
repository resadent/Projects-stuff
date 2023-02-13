function checkCashRegister(price, cash, cid) {
  let out={},amountToReturn=cash-price,amountReturned=0.0;
  let units={
    'PENNY':0.01,'NICKEL':0.05,
    'DIME':0.1,'QUARTER':0.25,
    'ONE':1.0,'FIVE':5.0,
    'TEN':10.0,'TWENTY':20.0,'ONE HUNDRED':100.0
  }
  let totalMoney=0;
  for (let i=0;i<cid.length;i++){
    totalMoney+=cid[i][1];
  }
  
  if (amountToReturn > totalMoney){
    out.status='INSUFFICIENT_FUNDS';
    out.change=[];
    return out
  } else if (amountToReturn == totalMoney){
    out.status='CLOSED'
    out.change=cid;
    return out
  }

  out.status='';
  out.change=[];
  let i=cid.length-1;
  while (i>=0 && amountReturned != amountToReturn){
    let valueOfUnit=units[cid[i][0]]
    let amount=0;
    //console.log('there are',cid[i][1]/valueOfUnit,'units of',Object.keys(units)[i])
    while (roundTwo(amountReturned + valueOfUnit) <= roundTwo(amountToReturn) && cid[i][1]>0){
      amountReturned+=valueOfUnit;
      amount+=valueOfUnit;
      cid[i][1]-=valueOfUnit;
      //console.log('amountReturned',amountReturned,'amountToReturn',amountToReturn,'valofunit',valueOfUnit)
      amountReturned=roundTwo(amountReturned);
      amount=roundTwo(amount);
      if (roundTwo(amountReturned + valueOfUnit) == amountToReturn){
        //console.log('getting out');
      }
    }
    if (amount > 0){
      let newAddition=[]
      newAddition.push(Object.keys(units)[i])
      newAddition.push(amount)
      out.change.push(newAddition)
    }
    i--;
    }

  if (amountReturned == amountToReturn){
    out.status='OPEN';
    return out;
  } else {
    let newout={}
    newout.status='INSUFFICIENT_FUNDS';
    newout.change=[]
    return newout;
  }
}

//console.log(checkCashRegister(19.5, 20, [["PENNY", 0.01], ["NICKEL", 0], ["DIME", 0], ["QUARTER", 0], ["ONE", 0], ["FIVE", 0], ["TEN", 0], ["TWENTY", 0], ["ONE HUNDRED", 0]]));

console.log(checkCashRegister(19.5, 20, [["PENNY", 0.01], ["NICKEL", 0], ["DIME", 0], ["QUARTER", 0], ["ONE", 1], ["FIVE", 0], ["TEN", 0], ["TWENTY", 0], ["ONE HUNDRED", 0]]));

/*
ir cogiendo mas monedas mientras no nos pasemos
si nos pasamos, vamos a la moneda mas pequeña

paramos cuando hemos recorrido todo el array de monedas o hemos llegado al importe exacto

*/

function roundTwo(num){
  return Math.round(num * 100) / 100;
}