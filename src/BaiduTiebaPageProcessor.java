import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class BaiduTiebaPageProcessor implements PageProcessor {

	// 抓取网站的相关配置 
   private Site site=Site.me().setRetryTimes(3).setSleepTime(1000)
		   .setDomain("tieba.baidu.com")
		   .addCookie("BAIDUID","1A97EEDAEF2A0301566ACF077AA10621")
           .addCookie("BDUSS", "szZWFHVFh0NTh3RmlqTC03WUsyfndHaElaTn5Rd1paMlJJc1JsdzVQTHhIakJaSVFBQUFBJCQAAAAAAAAAAAEAAACfFWBCzqS7pLXEu6QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPGRCFnxkQhZMk")
           .addCookie("FP_UID", "4230aa7c159bb1188a54051edf17efbc")
           .addCookie("Hm_lpvt_287705c8d9e2073d13275b18dbd746dc", "1493733763")
           .addCookie("Hm_lvt_287705c8d9e2073d13275b18dbd746dc", "1493732430,1493733763")
           .addCookie("LONGID", "1113593247")
           .addCookie("STOKEN", "aa630d5daa1dca7fd8b6b2e0f45e7ed606a8284a547483c11876ae929c6b7cb6")
           .addCookie("TIEBAUID", "68763e2055bb74510a0d56a9")
           .addCookie("TIEBA_USERTYPE", "39141a92ab9bfbbbd231771c")
           .addCookie("bdshare_firstime", "1493733762353")
           .addCookie("bottleBubble", "1")
           .addCookie("wise_device", "0");
           
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	
	static int cnt_page=0;
	static int cnt_user=0;
	String concernum_temp;
	String concernednum_temp;
	double postnum_temp_temp;
	String barage_temp;
	String postnum_temp;
	@Override
	public void process(Page page) {
	    // 添加吧友空间链接
		//  1.原链接中/f?有问题  2.去掉&
		if(page.getUrl().regex("http://tieba\\.baidu\\.com.{3}kw=%E7%87%95%E5%B1%B1%E5%A4%A7%E5%AD%A6").match()){
			page.addTargetRequests(page.getHtml().xpath("//div[@id='content_leftList']").links()
					.regex("/home/main/.*")
					.replace("/home/main/","http://tieba.baidu.com/home/main/")
					.all());
			//添加其他页面
			page.addTargetRequests(page.getHtml().xpath("//div[@id='frs_list_pager']").links()
					.regex("//tieba.baidu.com.*")
					.replace("//tieba.baidu.com/","http://tieba.baidu.com/")
					.all());
			cnt_page++;
			//System.out.println(page.getResultItems());	
			//System.out.println("The cnt_page is "+cnt_page);
		}else{
			BaiduTiebaUser bduser=new BaiduTiebaUser();
			//获取空间链接
			bduser.setUrl(page.getUrl().toString());
			//获取吧友ID
			bduser.setId(page.getHtml().xpath("//div[@class='userinfo_title']/span[@class='userinfo_username']/text()")
			         .get());
			//设置吧友性别
			bduser.setSex(page.getHtml().regex("userinfo_sex_(\\w{4,6})").toString().equals("male") ? 1:0);
			
			//设置吧友发帖数 并修复原来贴数过万显示错误的bug
			postnum_temp=page.getHtml().regex("发贴:(.{1,4})万").toString();
			if(postnum_temp==null){
				bduser.setPostNum(Integer.parseInt(page.getHtml().regex("发贴:(\\d+)").toString()));	
			}else{
				postnum_temp_temp=Double.parseDouble(postnum_temp)*10000;
				bduser.setPostNum((int)postnum_temp_temp);
				//System.out.println(postnum_temp);
			}
			
		    //设置吧龄
			barage_temp=page.getHtml().regex("吧龄:(.{1,4})年").toString();
			if(barage_temp!=null){
				bduser.setBarAge(Double.parseDouble(barage_temp));
			}else{
				bduser.setBarAge(0.0);
			}
			
			//设置关注数
			concernum_temp=page.getHtml().regex("home.concern.*home..target.._blank..(\\d+).*.home.fans").toString();
			if(concernum_temp!=null){
				bduser.setConcernNum(Integer.parseInt(concernum_temp));
			}else{
				bduser.setConcernNum(0);
			}
			
			//设置被关注数
			concernednum_temp=page.getHtml().regex("home.fans.*home..target.._blank..(\\d+)").toString();
			
			if(concernednum_temp!=null){
				bduser.setConcernedNum(Integer.parseInt(concernednum_temp));
			}else{
				bduser.setConcernedNum(0);
			}
			//年发贴数
			//bduser.setPostPerYear();
			//受欢迎系数
			//bduser.setWelcomeFactor();
			//抓取人数计数
			cnt_user++;
			System.out.println("The cnt_user is "+ cnt_user);
			//保存至数据库
		    //BaiduTiebaUserDAO.add(bduser);
			System.out.println(bduser);
		}
	}
	
	
	public static void main(String[] args){
	long startTime,endTime;
	startTime=System.currentTimeMillis();
	Spider.create(new BaiduTiebaPageProcessor())
	.addUrl("http://tieba.baidu.com/f?kw=%E7%87%95%E5%B1%B1%E5%A4%A7%E5%AD%A6&amp")
	//.addUrl("http://tieba.baidu.com/home/main/?un=%E6%AD%AA%E6%AD%AA%E5%B0%8F%E5%A2%A8%E8%BF%B9&ie=utf-8&fr=frs")
	//.addUrl("http://tieba.baidu.com/home/main/?un=YJZ_Destiny&ie=utf-8&fr=frs")  //吧龄为0
	//.addUrl("http://tieba.baidu.com/home/main?un=%E7%A5%9E%E5%9F%9F%E4%B9%9D%E5%B7%9E&ie=utf-8&fr=pb&ie=utf-8")  //帖子过万
	.thread(4)
	.run();
	endTime=System.currentTimeMillis();
	System.out.println("耗时约"+(endTime-startTime)/1000+"秒,共抓取"+cnt_page+"篇页面,共"+
			cnt_user+"名吧友");
	}
}
