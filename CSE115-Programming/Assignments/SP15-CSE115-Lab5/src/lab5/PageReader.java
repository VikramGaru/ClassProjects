package lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class PageReader {
	
	public String readPage(String address) {
		String all = "";
		try {
			URL url = new URL(address);
			BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openConnection().getInputStream()));
			String s = br.readLine();
			while (s != null) {
				all = all + s + '\n';
				s = br.readLine();
			}
		} catch (IOException e) {
			return "Oops, something went wrong.\nI tried to load "+address+" but failed:\n"+e.getClass().getCanonicalName();
		}
		return removeComments(all);
	}
	
	private String removeComments(String html) {
		String noComments = "";
		int state = 0;
		for (int i=0; i<html.length(); i++) {
			char ch = html.charAt(i);
			switch (state) {
			case 0:
				if (ch == '<') {
					state = 1;
				}
				else {
					noComments = noComments + ch;
				}
				break;
			case 1:
				if (ch == '!') {
					state = 2;
				}
				else {
					state = 0;
					noComments = noComments + '<' + ch;
				}
				break;
			case 2:
				if (ch == '>') {
					state = 3;
				}
				break;
			case 3:
				if (ch != '\n') {
					state = 0;
					noComments = noComments + ch;
				}
				break;
			}
		}
		return noComments;
	}
}
