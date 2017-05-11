
public class BaiduTiebaUser {
	String id;
	double barage;
	float postperyear;
	float welcomefactor;
	int concernnum ;
	int concernednum;
	int postnum;
	int sex;
	String url;
	//s get id
	public void setId(String id){
		this.id=id;
	}
    public String getId(){
    	return id;
    }
    //s get barage
    public void setBarAge(double barage){
    	this.barage=barage;
    }
    public double getBarAge(){
    	return barage;
    } 
    //s get concernnum
    public void setConcernNum(int ConcernNum){
    	this.concernnum=ConcernNum;
    }
    public int getConcernNum(){
    	return concernnum;
    }
    //s get concernednum
    public void setConcernedNum(int ConcernedNum){
    	this.concernednum=ConcernedNum;
    }
    public int getConcernedNum(){
    	return concernednum;
    }
    //s get postnum
    public void setPostNum(int PostNum){
    	this.postnum=PostNum;
    }
    public int getPostNum(){
    	return postnum;
    }
    //s get sex
    public void setSex(int Sex){
    	this.sex=Sex;
    }
    public int getSex(){
    	return sex;
    }
    /******ignore**********/
    public void  setPostPerYear(){
    	//this.postperyear=this.postnum/this.barage;
    }
    public float getPosrPerYear() {
		return postperyear;
	}   
    public void setWelcomeFactor(){
    	this.welcomefactor=this.concernednum/this.concernnum;
    }
    public float getWelcomeFactor(){
    	return welcomefactor;
    }
    /*******ignore*********/
    
    //s get url
    public void setUrl(String Url){
    	this.url=Url;
    	
    }
    public String getUrl(){
    	return url;
    }
    @Override
	public String toString() {
		return "BaiduTiebaUser [id=" + id + ", sex=" + sex + ", barage=" + barage + ", postnum=" + postnum + ", concernnum="
				+ concernnum + ", concerednnum=" + concernednum +", url="+url+ "]";
	}
}
