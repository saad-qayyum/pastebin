import java.io.*; 
public class PasteDto implements Serializable{ 

public String pasteUri; 
public String added; 
public String title; 
public int hits;
public int expiration;
public int user;
public String content;
public String syntax;


public PasteDto() { 
pasteUri="";
added="";
title="";
hits=0;
expiration=0;
user=0;
content="";
syntax="";
}

public void setPasteUri(String p){
    pasteUri = p;
}

public void setAdded(String a){
    added = a;
}

public void setTitle(String p){
    title = p;
}

public void setHits(int p){
    hits = p;
}
public void setExpiration(int p){
    expiration = p;
}
public void setUser(int p){
    user = p;
}

public void setContent(String p){
    content = p;
}

public void setSyntax(String p){
    syntax = p;
}


public String getPasteUri(){
    return pasteUri ;
}

public String getAdded(){
    return added ;
}

public String getTitle(){
    return title ;
}

public int getHits(){
    return hits ;
}
public int getExpiration(){
    return expiration ;
}
public int getUser(){
    return user ;
}

public String getContent(){
    return content ;
}

public String getSyntax(){
    return syntax ;
}


}