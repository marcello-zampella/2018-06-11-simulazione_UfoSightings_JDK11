package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.AnnoNumero;
import it.polito.tdp.ufo.model.Collegamento;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public ArrayList<AnnoNumero> getAnnoNumero() {
		String sql = "SELECT YEAR(s.datetime) AS anno, COUNT(*) AS numero " + 
				"from sighting s " + 
				"WHERE s.country='us' " + 
				"GROUP BY anno " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ArrayList<AnnoNumero> list = new ArrayList<AnnoNumero>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new AnnoNumero(
						res.getInt("anno"),
						res.getInt("numero")
						)) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public ArrayList<String> getStatesByAnno(int anno) {
		String sql = "SELECT s.state AS stato " + 
				"from sighting s " + 
				"WHERE s.country='us' AND YEAR(s.datetime)=? " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ArrayList<String> list = new ArrayList<String>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(
						res.getString("stato")
						) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public ArrayList<Collegamento> getAllCollegamenti(int anno) {
		String sql = "SELECT DISTINCT s1.state AS s1, s2.state AS s2 " + 
				"from sighting s1, sighting s2 " + 
				"WHERE s1.country='us' AND s2.country='us' AND YEAR(s1.datetime)=? AND  YEAR(s2.datetime)=? and s1.datetime>s2.datetime " + 
				"AND s1.state<>s2.state " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, anno);
			ArrayList<Collegamento> list = new ArrayList<Collegamento>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(
						new Collegamento(res.getString("s1"),res.getString("s2"))
						) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}


}
