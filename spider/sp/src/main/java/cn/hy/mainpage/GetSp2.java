package cn.hy.mainpage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetSp2 {
	/**
	 * @author hy
	 * @param args
	 * @throws Exception
	 * 
	 * 1.B站视频爬取
	 * 2.首先抓包  找到实际flv视屏地址  /upgcxcode/73/87/41148773/41148773-1-64.flv?expires=1530696000&platform=pc&ssig=d-Uqu8cWT....  就是这个
	 * 3.在fiddler中提取reffer的地址  添加进来
	 * 4.修改浏览器报头
	 * 5.还是采用IO读写的方式处理
	 */
	public static void main(String[] args) throws Exception {
		File file=new File("D:\\a.flv");
		file.createNewFile();
		String url = "https://cn-zjhz-cmcc-v-05.acgvideo.com/upgcxcode/73/87/41148773/41148773-1-64.flv?expires=1530696000&platform=pc&ssig=d-Uqu8cWT33LScYhb-PXMw&oi=3747077235&nfa=VTtnXeacAlS5k6cLdWuWIA==&dynamic=1&hfa=2031047847&hfb=Yjk5ZmZjM2M1YzY4ZjAwYTMzMTIzYmIyNWY4ODJkNWI=&trid=f72c48ccb6ca4754a7d5cd945792d7c7&nfc=1";
		URL imgurl = new URL(url);
		URLConnection connection = imgurl.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		connection.setRequestProperty("Referer","https://www.bilibili.com/video/av24500142/?spm_id_from=333.334.bili_douga.9");
		connection.setConnectTimeout(10 * 1000);
		
		InputStream in = connection.getInputStream();
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream("D:\\a.flv"));
		byte[] buf = new byte[1024*1024];
		int size;
		long jd = 1;
		long st = (long) System.currentTimeMillis();
		while (-1 != (size = in.read(buf))) {
			System.out.println("size: "+size);
			out.write(buf, 0, size);			//目前只能每次写入16kb速度比较慢
			System.out.println("正在下载中-写入次数："+jd+"/次");
			System.out.println("已下载中："+jd*10694+"/kb");
			Thread.sleep(100000);
			jd++;
		}
		long et = (long) System.currentTimeMillis();
		int ys = ((int) (et - st))/1000;
		System.out.println("下载完毕  总计用时："+ ys +"-s");
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
