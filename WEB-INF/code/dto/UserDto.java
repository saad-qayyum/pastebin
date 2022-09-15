package dto;
import java.io.*;
import java.util.*;
 
public class UserDto implements Serializable{ 

public String username; 
public int hits;
public int count;
public int id;



public UserDto() { 
username="";
hits=0;
count=0;
id=0;
}

public void setUsername(String p){
    username = p;
}

public void setHits(int p){
    hits = p;
}
public void setCount(int p){
    count = p;
}
public void setId(int p){
    id = p;
}



public String getUsername(){
    return username ;
}

public int getHits(){
    return hits ;
}
public int getCount(){
    return count ;
}
public int getId(){
    return id ;
}



}