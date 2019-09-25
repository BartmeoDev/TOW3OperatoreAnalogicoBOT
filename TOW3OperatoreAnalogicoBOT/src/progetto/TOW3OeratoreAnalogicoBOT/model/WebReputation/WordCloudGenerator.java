package progetto.TOW3OeratoreAnalogicoBOT.model.WebReputation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

public class WordCloudGenerator {
	
	private String text;
	private ArrayList<String> stopwords = new ArrayList<String>();
	
	public WordCloudGenerator (String t) {
		this.text = t;
		this.populateStopwords();
	}
	
	
	public List<WordFrequency> getFrequencies (){
		
		ArrayList<String> good_words = new ArrayList<String>();
		
		String cleantext = text.replace(".", " ").replace(",", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\"", " ").replace("-", " ").replace(":", " ").replace("?", " ").replace("!", " ").replace("'", " ");
		
		for (String word : cleantext.split(" ")) {
			if (word.length()>1 && !stopwords.contains(word)) {
				good_words.add(word);
			}
		}
		
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		List<WordFrequency> wordFrequencies = null;
		wordFrequencies = frequencyAnalyzer.load(good_words);
		
		return wordFrequencies;
	}
	
	
	public BufferedImage generateCloud () {

		List<WordFrequency> wordFrequencies = null;
		wordFrequencies = this.getFrequencies();
		
		//WordFrequency wf = new WordFrequency("", 2);
		final Dimension dimension = new Dimension(400, 400);
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		wordCloud.setAngleGenerator(new AngleGenerator (-30,30,10));
		wordCloud.setPadding(3);
		wordCloud.setBackground(new CircleBackground(200));
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new LinearFontScalar(8, 40));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("C:\\Users\\fabio\\Desktop\\datarank_wordcloud_circle_sqrt_font.png");
		return wordCloud.getBufferedImage();
	}
	
	

	private void populateStopwords () {
		
		File file = new File ("stopwords-it.txt");
		
		try {
			FileReader reader = new FileReader (file);
			
			BufferedReader buffer = new BufferedReader (reader);
			String s;

			while ((s = buffer.readLine()) != null) {
				stopwords.add(s);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
