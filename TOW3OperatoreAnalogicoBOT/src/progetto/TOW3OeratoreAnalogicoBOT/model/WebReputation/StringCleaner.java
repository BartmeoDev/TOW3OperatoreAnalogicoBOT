package progetto.TOW3OeratoreAnalogicoBOT.model.WebReputation;


import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

public class StringCleaner {
	
	private String myString;
	
	private HashMap<String, String> HTMLcharsMap = new HashMap<String, String>();
	private ArrayList<String> stopwords = new ArrayList<String>();
	
	public StringCleaner (String s) {
		
		this.myString = s;
		
		this.populateCharsMap();
		this.populateStopwords();
		
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
	
	
	private void populateCharsMap () {

		HTMLcharsMap.put("&quot;", "\"");
		HTMLcharsMap.put("&amp;", "&");
		HTMLcharsMap.put("&deg;", "°");
		HTMLcharsMap.put("&pound;", "£");
		HTMLcharsMap.put("&Egrave;", "È");
		HTMLcharsMap.put("&Eacute;", "É");
		HTMLcharsMap.put("&agrave;", "à");
		HTMLcharsMap.put("&aacute;", "á");
		HTMLcharsMap.put("&egrave;", "è");
		HTMLcharsMap.put("&eacute;", "é");
		HTMLcharsMap.put("&igrave;", "ì");
		HTMLcharsMap.put("&iacute;", "í");
		HTMLcharsMap.put("&ograve;", "ò");
		HTMLcharsMap.put("&oacute;", "ó");
		HTMLcharsMap.put("&ugrave;", "ù");
		HTMLcharsMap.put("&uacute;", "ú");
		HTMLcharsMap.put("&rsquo;", "'");
		HTMLcharsMap.put("&lsquo;", "'");
		HTMLcharsMap.put("&ldquo;", "\"");
		HTMLcharsMap.put("&rdquo;", "\"");
		HTMLcharsMap.put("&hellip;", "...");
		HTMLcharsMap.put("&#8217;", "'");
		HTMLcharsMap.put("&#224;", "à");
		HTMLcharsMap.put("&#232;", "è");
		HTMLcharsMap.put("&#171;", "«");
		HTMLcharsMap.put("&#8211;", "–");
		HTMLcharsMap.put("&#187;", "»");
		HTMLcharsMap.put("&#249", "ù");
		HTMLcharsMap.put("&#242;", "ò");
		HTMLcharsMap.put("&#236;", "ì");
//		HTMLcharsMap.put("Ã²;", "ò");
//		HTMLcharsMap.put("Ã;", "à");
//		HTMLcharsMap.put("Ã¬;", "ì");
//		HTMLcharsMap.put("Ã¨;", "è");
//		HTMLcharsMap.put("Ã¹;", "ù");
		HTMLcharsMap.put("&#8220;", "\"");
		HTMLcharsMap.put("&#8221;", "\"");
		HTMLcharsMap.put("<p>", "");
		HTMLcharsMap.put("</p>", "");
		HTMLcharsMap.put("<br/>", "");
		HTMLcharsMap.put("<br>", "");
		HTMLcharsMap.put("</b>", "");
		HTMLcharsMap.put("<b>", "");
		HTMLcharsMap.put("<i>", "");
		HTMLcharsMap.put("</i>", "");
		HTMLcharsMap.put("<em>", "");
		HTMLcharsMap.put("</em>", "");
		HTMLcharsMap.put("<strong>", "");
		HTMLcharsMap.put("</strong>", "");
		HTMLcharsMap.put("<", "");
		HTMLcharsMap.put(">", "");
		HTMLcharsMap.put("&#46;", ".");
		HTMLcharsMap.put("&#44;", ",");
		HTMLcharsMap.put("&#200;", "È");
		HTMLcharsMap.put("&#37;", "%");
		HTMLcharsMap.put("&#40;", "(");
		HTMLcharsMap.put("&#41;", ")");
		HTMLcharsMap.put("&#45;", "-");
		HTMLcharsMap.put("&#59;", ";");
		HTMLcharsMap.put("&#233;", "é");
		HTMLcharsMap.put("&#39;", "'");
		HTMLcharsMap.put("&#226;", "â");
		HTMLcharsMap.put("&#47;", "/");
		HTMLcharsMap.put("&#58;", ":");
		HTMLcharsMap.put("&#8201;", "«");
		HTMLcharsMap.put("&#8202;", "»");
		HTMLcharsMap.put("&#63;", "?");
		HTMLcharsMap.put("&#8230;", "...");
		HTMLcharsMap.put("&nbsp;", " ");
		HTMLcharsMap.put("&ndash;", "-");
	}
	
	
	public String extractText (String testata) {
		
		if (testata.compareTo("www.ilsole24ore.com")==0) {
			String text = this.rawCleanSole();
			if (text == null) this.rawClean();
		}
		
		else {
			this.rawClean();
		}
		
		this.replaceHTMLchars();
		
		return this.myString;
	}
	
	
	public String replaceHTMLchars () {
		
		for (String s : HTMLcharsMap.keySet()) {
			myString = myString.replace(s, HTMLcharsMap.get(s));
			
		}
				
		return this.myString;
	}
	
	
	/*
	public void generateCloud (String s) {
		
		ArrayList<String> good_words = new ArrayList<String>();
		
		String cleantext = myString.replace(".", " ").replace(",", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\"", " ").replace("-", " ").replace(":", " ").replace("?", " ").replace("!", " ").replace("'", " ");
		
		for (String word : cleantext.split(" ")) {
			if (word.length()>1 && !stopwords.contains(word)) {
				good_words.add(word);
			}
		}
		
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		List<WordFrequency> wordFrequencies = null;
		wordFrequencies = frequencyAnalyzer.load(good_words);
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
		
	}
	
	*/
	
	private String rawClean () {
		
		//String rawString2 = this.myString;
		String rawString = "";
		String maybetext = "";
		
		String[] links = StringUtils.substringsBetween(myString, "<a ", "</a>");
		if (links != null)
		for (String link : links) {
			myString = myString.replace("<a " + link + "</a>", "");
			//System.out.println(link);
		}

		String[] html1 = StringUtils.substringsBetween(myString, "<p ", "/p>");
		String[] html2 = StringUtils.substringsBetween(myString, "<p>", "/p>");
		

		if (html1 != null)
			for (String s1 : html1) {
				maybetext += "<p " + s1 + "/p>" + "\n##";
			}
		
		if (html2 != null)
			for (String s2 : html2) {
				maybetext += "<p>" + s2 + "/p>" + "\n##";
			}
		
		maybetext = maybetext.replace("{", "").replace("}", "");
		String[] cuts1 = StringUtils.substringsBetween(maybetext, "<p ", "\">");
		String[] cuts2 = StringUtils.substringsBetween(maybetext, "<div", "/div>");
		
		if (cuts1 != null) {
			for (String cut1 : cuts1) {
				maybetext = maybetext.replaceFirst("<p " + cut1 + "\">", "");
			}
		}
		
		if (cuts2 != null) {
			for (String cut2 : cuts2) {
				maybetext = maybetext.replace("<div" + cut2 + "/div>", "");
			}
		}
		
		
		
		for (String par : maybetext.split("##")) {
			
			if (par.trim().length()<50) continue;
			
			if (par.contains("class=\"")) {
				
				if (par.trim().length()<200) continue;
				
//				String[] links = StringUtils.substringsBetween(par, "<a ", "</a>");
//				for (String link : links) {
//					maybetext = maybetext.replace("<a " + link + "</a>", "");
//					//System.out.println(link);
//				}
			}
			
			String[] spans = StringUtils.substringsBetween(par, "<span", ">");
			
			if (spans == null) {
				rawString += par;
				continue;
			}
			
			for (String span : spans) {
				par = par.replace("<span" + span + ">", "");
			}
		}		
		
		myString = rawString;
		this.replaceHTMLchars();
		
		return this.myString;
	}
		
	private String rawCleanSole () {
				
		String[] contents = StringUtils.substringsBetween(myString, "\"articleBody\": \"", "\",");
		String rawText = "";
		
		if (contents == null) return null;
				
		for (String content : contents) {
			rawText += content;
		}
		
		myString = rawText;
		
		return myString;
		
	}
	
	
}