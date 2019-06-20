package earthquakeReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.Date;
import java.util.Timer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class EarthquakeReader {

	private static String path;
	private static DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");

	public static void main(String[] args) {

		path = args[0];

		Timer timer = new Timer("Earthquake Timer");
		TimerTask task = new TimerTask() {
			public void run() {

				Date date = new Date();
				String fileName = df.format(date) + ".txt";
				
				File output = new File(path + fileName);
				//File output  = new File("/Users/maxwellchen/Documents/Intern_Work/NR/timerResults/t.txt");
				
				try {
					output.createNewFile();

					FileWriter fw = new FileWriter(output);

					Document doc = Jsoup.connect("http://news.ceic.ac.cn/").get();
					Elements table = doc.getElementsByTag("tr");
					for (int i = 0; i < table.size(); i++) {
						Document entry = Jsoup.parse(table.get(i).toString());
						fw.write(entry.body().text() + "\n");
					}
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		long period = 1000 * 60 * 60;
		timer.schedule(task, 0, period);
	}
}
