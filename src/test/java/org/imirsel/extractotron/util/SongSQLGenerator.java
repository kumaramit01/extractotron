package org.imirsel.extractotron.util;

import java.io.File;

public class SongSQLGenerator {
	
	public static void main(String[] args){
		//String dirName = "/Users/amitku/Music/iTunes/iTunes Music/The Rolling Stones/More Hot Rocks (Big Hits & Fazed Cookies) (disc 1)";
		//String dirName ="/Users/amitku/Music/iTunes/iTunes Music/Tracy Chapman/Crossroads";
		String dirName ="/Users/amitku/Music/iTunes/iTunes Music/The Doors/The Best of the Doors (disc 2)";
		File dir = new File(dirName);
		if(!dir.exists() || !dir.isDirectory()){
			throw new IllegalArgumentException(dir.getAbsolutePath());
		}
		
		File[] files =dir.listFiles();
		
		for(File file:files){
			System.out.println("insert into song (name,location) values (\""+file.getName()+"\",\""+ file.getAbsolutePath()+"\");\n");
		}
		
	}

}
