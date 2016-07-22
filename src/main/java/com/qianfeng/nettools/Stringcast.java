package com.qianfeng.nettools;
public class Stringcast {
  public static String[] stringcast(String string,char c){
	  
	int count=0;
	
	if(string.indexOf(c)==-1){
		return new String[]{string};
	}
	char[] cs=string.toCharArray();
	for (int i = 0; i < cs.length-1; i++) {
		if(cs[i]==c){
			count++;
		}
	}
	String[] strArray=new String[count+1];
	int k=0,j=0;
	String str=string;
	if((k=str.indexOf(c))==0){
		str=string.substring(k+1);
		
	}
	if(str.indexOf(c)==-1){
		return new String[]{str};
	}
	
	while((k=str.indexOf(c))!=-1){
		strArray[j++]=str.substring(0,k);
		str=str.substring(k+1);
		if((k=str.indexOf(c))==-1&&str.length()>0){
			strArray[j++]=str.substring(0);
		}
	}
	
	return strArray;
}
}