package org.imirsel.extractotron.webapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NemaCollectionDump {
	
	String collectionListSql ="select * from collection";
	String trackListSql = "select track_id from collection_track_link where collection_id=?";
	String fileListSql = "select path from file where track_id=?";
	String listSql="select file.path, file.track_id from file inner join " +
			"collection_track_link on file.track_id =collection_track_link.track_id " +
			"where collection_track_link.collection_id=";
	
	
	private Connection readConnect = null;
	private Connection writeConnect = null;
	
	
	public static void main(String[] args) throws Exception{
		NemaCollectionDump ncd = new NemaCollectionDump();
		ncd.initReadConnection();
		ncd.initWriteConnection();
		List<Collection> nemaCollection = new ArrayList<Collection>();
		ncd.readCollectionInfo(nemaCollection);
		ncd.saveCollectionInfo(nemaCollection);
		// this time you get the collection ids
		List<Collection> extractortronCollection=ncd.readCollectionInfoFromSaved();
		// for each collection 
		
		for(Collection collection: nemaCollection){
			System.out.println("Getting Songs for the collection: " + collection.id);
			List<Song> nemaSongs = ncd.getNemaSongs(collection.id);
			System.out.println("Found: " + nemaSongs.size() + " songs.");
			ncd.saveSongsForCollection(nemaSongs,collection.id);
		}
		
		
	}
	
	
	private void saveSongsForCollection(List<Song> nemaSongs, int collection_id) throws SQLException {
		PreparedStatement preparedStatement = writeConnect
		.prepareStatement("insert into  song (name,location) values (?, ?)",Statement.RETURN_GENERATED_KEYS);
		PreparedStatement collectionSongPrep = writeConnect
		.prepareStatement("insert into collection_song values(?,?)");
		
		for(Song song:nemaSongs){
			preparedStatement.setString(1, song.name);
			preparedStatement.setString(2, song.location);
			preparedStatement.executeUpdate();
			ResultSet keys = preparedStatement.getGeneratedKeys(); 
			keys.next();
			int key = keys.getInt(1);
			keys.close();
			
			
			// insert into the collection_song
			collectionSongPrep.setInt(1, collection_id);
			collectionSongPrep.setInt(2, key);
			collectionSongPrep.execute();
			
			
		}
		
	}


	private  List<Song> getNemaSongs(int collection_id) {
		List<Song> songList = new ArrayList<Song>();
		ResultSet resultSet = null;
		Statement statement = null;
		try{
			// Statements allow to issue SQL queries to the database
			statement=readConnect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement
					.executeQuery(listSql+collection_id+";");
			
			while (resultSet.next()) {
				String path = resultSet.getString("path");
				String name= resultSet.getString("track_id");
				Song song = new Song();
				song.name= name;
				song.location=path;
				songList.add(song);
			}
			
			
			
		}catch(Exception ex){
			
		}
		
		return songList;
	}


	private List<Collection> readCollectionInfoFromSaved() throws Exception {
		Statement statement=null;
		ResultSet resultSet = null;
		List<Collection> collectionList = new ArrayList<Collection>();
		
		try{
		statement=writeConnect.createStatement();
		// Result set get the result of the SQL query
		resultSet=statement
				.executeQuery("select * from collection");
		
		getCollections(resultSet,collectionList);
		} catch (Exception e) {
			throw e;
		} finally {
			if(statement!=null)
				statement.close();
			if(resultSet!=null)
				resultSet.close();
		}
		
		return collectionList;
		
	}


	private void saveCollectionInfo(List<Collection> collectionList) throws SQLException {
		PreparedStatement preparedStatement = writeConnect
				.prepareStatement("insert into  collection (name,description,userDefined) values (?, ?,False)");
		for(Collection collection:collectionList){
			preparedStatement.setString(1, collection.name);
			preparedStatement.setString(2, collection.description);
			preparedStatement.executeUpdate();
		}
	}


	
	public void initWriteConnection() throws Exception {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			writeConnect = DriverManager
					.getConnection("jdbc:mysql://localhost/extractotronv1?"
							+ "user=root&password=newgslis");
	}
	
	public void initReadConnection() throws ClassNotFoundException, SQLException{
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			readConnect = DriverManager
					.getConnection("jdbc:mysql://nema-dev.lis.illinois.edu/nemadatarepository040?"
							+ "user=nema_user&password=reduxer101");

	}
	
	public void readCollectionInfo(List<Collection> collectionList) throws Exception {
		ResultSet resultSet = null;
		Statement statement = null;
		try{
			// Statements allow to issue SQL queries to the database
			statement=readConnect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement
					.executeQuery("select * from collection");
			getCollections(resultSet,collectionList);

			// PreparedStatements can use variables and are more efficient
			//preparedStatement = connect
			//		.prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			// Parameters start with 1
			//preparedStatement.setString(1, "Test");
			//preparedStatement.setString(2, "TestEmail");
			//preparedStatement.setString(3, "TestWebpage");
			//preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			//preparedStatement.setString(5, "TestSummary");
			//preparedStatement.setString(6, "TestComment");
			//preparedStatement.executeUpdate();

			//preparedStatement = connect
			//		.prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
			//resultSet = preparedStatement.executeQuery();
			//writeResultSet(resultSet);

			// Remove again the insert comment
			//preparedStatement = connect
			//.prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
			//preparedStatement.setString(1, "Test");
			//preparedStatement.executeUpdate();
			
			//resultSet = statement
			//.executeQuery("select * from FEEDBACK.COMMENTS");
			//writeMetaData(resultSet);
			
		} catch (Exception e) {
			throw e;
		} finally {
			if(statement!=null)
				statement.close();
			if(resultSet!=null)
				resultSet.close();
		}

	}

	
	
	private void getCollections(ResultSet resultSet, List<Collection> collectionList) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			String name = resultSet.getString("name");
			String description = resultSet.getString("description");
			int id = resultSet.getInt("id");
			Collection collection = new Collection();
			collection.id = id;
			collection.description=description;
			collection.name = name;
			collectionList.add(collection);
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			/*if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}*/

			if (readConnect != null) {
				readConnect.close();
			}
		} catch (Exception e) {

		}
	}
	
	
	class Collection {
		int id;
		String name;
		String description;
	}
	
	class Song{
		int id;
		String name;
		String location;
	}



}
