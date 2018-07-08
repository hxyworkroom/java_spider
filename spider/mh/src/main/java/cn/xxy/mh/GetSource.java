package cn.xxy.mh;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetSource {
	private List lsls = new ArrayList();
	public static void main(String[] args) throws Exception {
		new GetSource().getAcope();
	}
	
	
	//获取分类页面第一页所有的
	public void getAcope(){
		try {
			String url = "http://www.5177mai.com/fenlei/";
			Connection conn=Jsoup.connect(url);
			conn.header("Referer","http://www.5177mai.com/");
			Document doc=conn.get();
			Elements getpageURLlist=doc.getElementsByClass("comic_list");
			Elements getA_node = getpageURLlist.select("a");
			Iterator<Element> list = getA_node.iterator();
			String mhtitle ;
			String lianjie = "" ;
			String tpLianjie ;
			
			for (int i = 0; i < 25; i++) {
				list.next();
			}
			while (list.hasNext()) {
				Element element = list.next();
				mhtitle = element.text();
				lianjie = element.attr("href");
				tpLianjie =element.select("img").attr("src");
				System.out.println(mhtitle+"::"+lianjie+"::"+tpLianjie);
				lsls.add(lianjie);
				String bag = "D:\\imgss\\"+mhtitle;
				File file = new File(bag);
				Boolean f = file.exists();
				if (!f) {
					file.mkdir();
				}
				GetSource.getAseparateImage(url, tpLianjie, bag);//创建好文件夹   并下载封面图片
				System.out.println("~~~~~~~文件夹创建完成~~~~~~~");
				Thread.sleep(new Random().nextInt(1000)+2000);
				new GetSource().getEveryPageUrl(lianjie,bag);
			}
			System.out.println("第一大版块爬取结束~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
		}catch(Exception e) {
			for (Object object : lsls) {
				System.out.println(object.toString());
			}
		}

	}
	
	//获取每一话的连接
	public String getEveryPageUrl(String urlcs,String bag) throws Exception {
		String first = urlcs.substring(0, 1);
		System.out.println(first);
		String url ="";
		if (first.equals("/")) {		
			url ="http://www.5177mai.com" + urlcs;
		}else {		
			url = urlcs;
		}
		System.out.println(url+"看看");
		Connection conn=Jsoup.connect(url);
		conn.header("Referer","http://www.5177mai.com/fenlei/");
		Document doc=conn.get();
		
		Elements getpageURLlist=doc.getElementsByClass("article_comic");
		Elements getA_node = getpageURLlist.select("a");
		Iterator<Element> list = getA_node.iterator() ;
		String meiyihua_url = "";
		String title ;
		while (list.hasNext()) {
			Element element = list.next();
			//获取每一话连接
			meiyihua_url = element.attr("href");
			title = element.attr("title");
//			System.out.println(title+"::"+meiyihua_url);
			Thread.sleep(2000);
			new GetSource().getEveryImgeUrl(meiyihua_url,bag,title);
		}
		return meiyihua_url;
	}
	
	//获取每一话图片连接
	public void getEveryImgeUrl(String meiyihua_url,String bag,String title) throws Exception {
		Connection conn=Jsoup.connect(meiyihua_url);
		conn.header("Referer",meiyihua_url);
		conn.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		Document doc=conn.get();
		Elements imgUrlDiv=doc.getElementsByClass("comic_pic_box");
		Elements imgUrl = imgUrlDiv.select("img");
		String imgurl = imgUrl.attr("src");
		Iterator<Element> list = imgUrl.iterator();
		Element element ;
		String urls = "";
		
		String wenjianjia_name = bag + "\\" + title;
		File file = new File(wenjianjia_name);
		file.mkdir();
		int js =0;
		while (list.hasNext()) {
			element = list.next();
			urls = element.attr("src");
			System.out.println("我看看"+urls);
			String first = urls.substring(0, 1);
			if (first.equals("/")) {		
				break;
			}
			Thread.sleep(3000);
			try {
				new GetSource().getImge(urls,bag,title,meiyihua_url,js+"");
			}catch(Exception e) {
				continue;
			}
			js++;
		}
	}
	
	//获取每一张图片
	public void getImge(String urla,String bagImage,String sl,String myh,String js) throws Exception {
		String url = urla;
		String wjdz = bagImage + "\\" + sl + "\\" + js +".jpg";
		System.out.println("看看图片文件地址："+wjdz);
		File file = new File(wjdz);
		file.createNewFile();
		
		URL imgurl = new URL(url);
		URLConnection connection = imgurl.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		connection.setRequestProperty("Referer", myh);
		connection.setConnectTimeout(10 * 1000);
		
		
		InputStream in = connection.getInputStream();
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(wjdz));
		byte[] buf = new byte[1024];
		int size;
		while (-1 != (size = in.read(buf))) {
			out.write(buf, 0, size);
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out = null;
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in = null;
		}
		System.out.println("下完一张~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public static void getAseparateImage(String refUrl,String imgUrl,String bagLocation) throws Exception {
		URL imgurl = new URL(imgUrl);
		URLConnection connection = imgurl.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		connection.setRequestProperty("Referer",refUrl);
		connection.setConnectTimeout(10 * 1000);
		
		InputStream in = connection.getInputStream();
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(bagLocation+"\\titleImage.jpg"));
		byte[] buf = new byte[1024];
		int size;
		while (-1 != (size = in.read(buf))) {
			out.write(buf, 0, size);
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out = null;
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in = null;
		}
	}
}
















