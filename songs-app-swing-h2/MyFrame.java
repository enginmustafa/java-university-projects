import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyFrame extends JFrame{
	
	Connection conn = null;
	PreparedStatement state = null;
	int id = -1;
	int saveParameter=0;
	String downPanelSql="select Song_Id,Title,Nname,Genre,Release_Date "
			 + "from Songs s join Artists a "
			 + "on s.Artist_Id=a.Artist_Id "
			 + "join Genres g "
			 + "on s.Genre_Id=g.Genre_Id";
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel extraPanel=new JPanel();
	JPanel extraButtonsPanel=new JPanel();
	
	JButton addBtn = new JButton("Add");
	JButton delBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	JButton searchBtn = new JButton("Search");
	JButton resBtn = new JButton("Reset");
	

	JButton extraEditBtn = new JButton("Edit");
	JButton saveBtn = new JButton("Save");
	JButton extraDelBtn = new JButton("Delete");
	JButton extraAddBtn = new JButton("Add");
	JButton newGenreBtn = new JButton("New Genre");
	JButton newArtistBtn = new JButton("New Artist");



	JTable table = new JTable();
	JScrollPane scroller = new JScrollPane(table);
	
	
	JLabel titleLabel = new JLabel("Title:");
	JLabel releaseDateLabel = new JLabel("Release Date:");
	JLabel genreLabel = new JLabel("Genre:");
	JLabel artistLabel = new JLabel("Artist:");
	JLabel fNameLabel = new JLabel("Full name:");
	JLabel nNameLabel = new JLabel("Nickname:");
	JLabel newGenreLabel = new JLabel("Genre:");

	
	
	JTextField titleTField = new JTextField();
	JTextField releaseDateTField = new JTextField();
	JTextField fNameTField = new JTextField();
	JTextField nNameTField = new JTextField();
	JTextField newGenreTField = new JTextField();


	String[] content = {""};

	JComboBox<String> genreCombo = new JComboBox<>(content);
	JComboBox<String> artistCombo = new JComboBox<>(content);

	
	public MyFrame() {
		
		editBtn.setEnabled(false);
		delBtn.setEnabled(false);
		extraAddBtn.setEnabled(false);
		fNameTField.setEnabled(false);
		nNameTField.setEnabled(false);
		newGenreTField.setEnabled(false);
		saveBtn.setEnabled(false);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(525,850);
		this.setLayout(new GridLayout(5, 1));
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
		this.add(extraPanel);
        this.add(extraButtonsPanel);
		
		//upPanel
		upPanel.setLayout(new GridLayout(5, 2));
		upPanel.add(titleLabel);
		upPanel.add(titleTField);
		upPanel.add(releaseDateLabel);
		upPanel.add(releaseDateTField);
		upPanel.add(genreLabel);
		upPanel.add(genreCombo);
		upPanel.add(artistLabel);
		upPanel.add(artistCombo);	



		//midPanel
		midPanel.add(addBtn);
		midPanel.add(delBtn);
		midPanel.add(editBtn);
		midPanel.add(searchBtn);
		midPanel.add(resBtn);
		
		resBtn.setEnabled(false);

		addBtn.addActionListener(new AddAction());
		delBtn.addActionListener(new DeleteAction());
		editBtn.addActionListener(new EditAction());
		searchBtn.addActionListener(new SearchAction());
		resBtn.addActionListener(new ResetAction());

		//downPanel
		downPanel.add(scroller);
		scroller.setPreferredSize(new Dimension(450, 130));
		
	
		table.setModel(DBConnector.getAllModel(downPanelSql));
		table.addMouseListener(new MouseTableAction());
		
		fillCombos("select * from genres",genreCombo,1);
		fillCombos("select * from artists",artistCombo,2);
		
		//extraPanel
		extraPanel.setLayout(new GridLayout(5,2));
		extraPanel.add(fNameLabel);
		extraPanel.add(fNameTField);
		extraPanel.add(nNameLabel);
	    extraPanel.add(nNameTField);
		extraPanel.add(newGenreLabel);
		extraPanel.add(newGenreTField);

		//extraButtonsPanel
        extraButtonsPanel.add(extraAddBtn);
		extraButtonsPanel.add(extraEditBtn);
		extraButtonsPanel.add(saveBtn);
		extraButtonsPanel.add(extraDelBtn);
		extraButtonsPanel.add(newGenreBtn);
		extraButtonsPanel.add(newArtistBtn);
		
		newGenreBtn.addActionListener(new NewGenreAction());
		newArtistBtn.addActionListener(new NewArtistAction());
		extraAddBtn.addActionListener(new ExtraAddAction());
		extraDelBtn.addActionListener(new ExtraDeleteAction());
		extraEditBtn.addActionListener(new ExtraEditAction());
		saveBtn.addActionListener(new SaveAction());





	}//end constructor
	
	class MouseTableAction implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			if(e.getClickCount() > 1) {
				editBtn.setEnabled(true);
				delBtn.setEnabled(true);

				titleTField.setText(table.getValueAt(row, 1).toString());
				releaseDateTField.setText(table.getValueAt(row, 4).toString());
				String genre = table.getValueAt(row, 3).toString();
				genreCombo.setSelectedItem(genre);
				String artist = table.getValueAt(row, 2).toString();
                artistCombo.setSelectedItem(artist);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sql = "delete from songs where song_id=?";
			conn = DBConnector.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				id = -1;
				delBtn.setEnabled(false);
				editBtn.setEnabled(false);
				table.setModel(DBConnector.getAllModel(downPanelSql));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			clearForm();
			
		}
		
	}
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String title = titleTField.getText();
			int releaseDate = Integer.parseInt(releaseDateTField.getText());
			String genre = genreCombo.getSelectedItem().toString();
			String artist = artistCombo.getSelectedItem().toString();
			String sql = "insert into songs values (null,?,?,("
					              + "select Artist_Id from Artists "
					              + "where Nname=?),"
					              + "select Genre_Id from Genres "
					              + "where Genre=?);";
			
			
			
			conn = DBConnector.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, title);
				state.setInt(2, releaseDate);
				state.setString(3,artist);
				state.setString(4,genre);
			

				state.execute();
				table.setModel(DBConnector.getAllModel(downPanelSql));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			clearForm();	
		}
		
	}//end AddAction
	
	class EditAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String title = titleTField.getText();
			
			int releaseDate = Integer.parseInt(releaseDateTField.getText());
			String genre = genreCombo.getSelectedItem().toString();
			String artist = artistCombo.getSelectedItem().toString();
			
			String sql = "update Songs set"
					   + "(Title,Release_Date,Artist_Id,Genre_Id) "
					   + "= (?,?,(select Artist_Id from Artists "
					   + "where Nname=?),"
					   + "(select Genre_Id from Genres "
					   + "where Genre=?)) "
					   + "where Song_Id=?";
			
			conn = DBConnector.getConnection();
			try {
				state = conn.prepareStatement(sql);
				state.setString(1,title);
				state.setInt(2,releaseDate);
				state.setString(3,artist);
				state.setString(4,genre);
				state.setInt(5,id);

				state.execute();
				id=-1;
				editBtn.setEnabled(false);
				delBtn.setEnabled(false);

				
				table.setModel(DBConnector.getAllModel(downPanelSql));
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			clearForm();
			
		}
		
	}//end EditAction
	
	class SearchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String genre = genreCombo.getSelectedItem().toString();
			String artist = artistCombo.getSelectedItem().toString();
			String searchSql="select Song_Id,Title,Nname,Release_Date,Genre "
					        + "from Songs s join Genres g "
					        + "on s.Genre_Id=g.Genre_Id "
				        	+ "join Artists a "
				        	+ "on s.Artist_Id=a.Artist_Id ";
			
			//no data provided
			if(genre.equals("") & artist.equals("")) {
				JOptionPane.showMessageDialog (null, "No search data provided.", "Search", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//provided data for both
			else if(!genre.equals("") & !artist.equals("")) {
				searchSql+="where Genre='"+genre+"' and Nname='"+artist+"'";
			}
			//provided data for gender only
			else if(!genre.equals("")) {
				searchSql+="where Genre='"+genre+"'";
			}
			//provided data for artist only
			else {
				searchSql+="where Nname='"+artist+"'";
			}
			
			MyModel searchData = DBConnector.getAllModel(searchSql);
			if(searchData.getRowCount()==0) {
				JOptionPane.showMessageDialog (null, "No results matching filters found.", "Search", JOptionPane.INFORMATION_MESSAGE);
                return;
			}
			
			table.setModel(searchData);
            resBtn.setEnabled(true);
            addBtn.setEnabled(false);

			
			clearForm();	
			
			JOptionPane.showMessageDialog (null, "Search results listed below.", "Search", JOptionPane.INFORMATION_MESSAGE);

		 }
		
		
	}//end SearchAction
	
	class ResetAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			resBtn.setEnabled(false);
            addBtn.setEnabled(true);
            editBtn.setEnabled(true);
            delBtn.setEnabled(true);
            
			table.setModel(DBConnector.getAllModel(downPanelSql));

		}
		
	}	
	class NewGenreAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			extraAddBtn.setEnabled(true);
			newGenreTField.setEnabled(true);
			fNameTField.setEnabled(false);
			nNameTField.setEnabled(false);
		}
		
	}//end NewGenreAction
	class NewArtistAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			extraAddBtn.setEnabled(true);
			fNameTField.setEnabled(true);
			nNameTField.setEnabled(true);
			newGenreTField.setEnabled(false);

			
		}
		
	}//end NewArtistAction
	class ExtraAddAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String genre=newGenreTField.getText();
			String fName=fNameTField.getText();
		    String nName=nNameTField.getText();
		    
		    String sql=null;
			conn = DBConnector.getConnection();
		    
		    if(!genre.equals("")) {
		    	try {
		    		sql="insert into genres values(null,?);";
					state = conn.prepareStatement(sql);
					state.setString(1,genre);
					state.execute();
					fillCombos("select * from genres",genreCombo,1);

					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    }
		    else {
		    	try {
		    		sql="insert into artists values(null,?,?);";
					state = conn.prepareStatement(sql);
					
					state.setString(1,fName);
					state.setString(2,nName);

					state.execute();
					fillCombos("select * from artists",artistCombo,2);

					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    } {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			clearExtraForm();
		}
		
	}//end ExtraAddAction
	class ExtraDeleteAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String genre = genreCombo.getSelectedItem().toString();
			String artist = artistCombo.getSelectedItem().toString();
			String sql = null;
			int parameter=0;
			
			//no objects chosen
			if(genre.equals("") && artist.equals("")) {
				JOptionPane.showMessageDialog (null, "No genre/artist is selected.", "Delete", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//delete artist
			else if(genre.equals("") && !artist.equals("")) {
				sql="delete from artists where nName=?";
				parameter=1;
				artistCombo.setSelectedIndex(0);
			}
			//delete genre
			else if(!genre.equals("") && artist.equals("")) {
				sql="delete from genres where Genre=?";
				parameter=2;
				genreCombo.setSelectedIndex(0);
			}
			//multiple objects chosen
			else {
				JOptionPane.showMessageDialog (null, "Only one object at a time can be deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
                return;
			}
			
			conn = DBConnector.getConnection();
			try {
				state=conn.prepareStatement(sql);
				switch(parameter) {
					case 1:{
				          state.setString(1,artist);
				          state.execute();
				          removeDeletedItemFromCombo(artistCombo,artist);
						  table.setModel(DBConnector.getAllModel(downPanelSql));
				          break;
					}
					case 2:{
					      state.setString(1,genre);
					      state.execute();
				          removeDeletedItemFromCombo(genreCombo,genre);
						  table.setModel(DBConnector.getAllModel(downPanelSql));
                          break;
					}
				}
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		}
		
	}//end ExtraDeleteAction
	class ExtraEditAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String genre = genreCombo.getSelectedItem().toString();
			String artist = artistCombo.getSelectedItem().toString();
			
			//no objects chosen
			if(genre.equals("") && artist.equals("")) {
				JOptionPane.showMessageDialog (null, "No genre/artist is selected.", "Edit", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//edit artist
			else if(genre.equals("") && !artist.equals("")) {
				saveParameter=1;
				newGenreTField.setEnabled(false);
				newGenreTField.setText("");

				fNameTField.setEnabled(true);
				nNameTField.setEnabled(true);
				nNameTField.setText(artist);
				MyModel data = DBConnector.getAllModel("select fName from Artists where nName='"+artist+"'");
                fNameTField.setText(data.getValueAt(0,0).toString());
				saveBtn.setEnabled(true);
				
			}
			//edit genre
			else if(!genre.equals("") && artist.equals("")) {
				saveParameter=2;
				fNameTField.setText("");
				nNameTField.setText("");
				fNameTField.setEnabled(false);
				nNameTField.setEnabled(false);
				
				newGenreTField.setEnabled(true);
				newGenreTField.setText(genre);
				saveBtn.setEnabled(true);

			}
			//multiple objects chosen
			else {
				JOptionPane.showMessageDialog (null, "Only one object at a time can be edited.", "Edit", JOptionPane.INFORMATION_MESSAGE);
                return;
			}
		} 
		
	}
	class SaveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String whereParameter=null;
			String genre = newGenreTField.getText();
			String fName = fNameTField.getText();
			String nName = nNameTField.getText();

			String sql = null;
			conn = DBConnector.getConnection();
			
         switch (saveParameter) {
                 case 1:
                	 whereParameter=artistCombo.getSelectedItem().toString();
     				sql="update artists set(fName,nName)=(?,?) where nName=?";
			        try {
			        	state=conn.prepareStatement(sql);
			        	state.setString(1,fName);
			        	state.setString(2,nName);
			        	state.setString(3,whereParameter);
			        	state.execute();
			        	
				        removeDeletedItemFromCombo(artistCombo,whereParameter);
						fillCombos("select * from artists",artistCombo,2);

			        } catch (SQLException e1) {
			        	// TODO Auto-generated catch block
				       e1.printStackTrace();
			        }
			        try {
			     		state.close();
			     		conn.close();
				        } catch (SQLException e1) {
				        	// TODO Auto-generated catch block
				        	e1.printStackTrace();
			         	}
                	 break;
                 case 2:
                	 whereParameter=genreCombo.getSelectedItem().toString();
     				sql="update genres set(Genre)=(?) where Genre=?";
			        try {
			        	state=conn.prepareStatement(sql);
			        	state.setString(1,genre);
			        	state.setString(2,whereParameter);
			        	state.execute();
			        	
				        removeDeletedItemFromCombo(genreCombo,whereParameter);
			        	fillCombos("select * from genres",genreCombo,1);
			        	
			        } catch (SQLException e1) {
			        	// TODO Auto-generated catch block
			         	e1.printStackTrace();
			        }
		         	try {
		     		state.close();
		     		conn.close();
			        } catch (SQLException e1) {
			        	// TODO Auto-generated catch block
			        	e1.printStackTrace();
		         	}
                	 break;
         }
         saveParameter=0;
     	 saveBtn.setEnabled(false);
		 clearForm();
		 clearExtraForm();
		}
		
	}
	
	private void clearForm() {
		titleTField.setText("");
		releaseDateTField.setText("");
		releaseDateTField.setText("");
	    genreCombo.setSelectedIndex(0);
		artistCombo.setSelectedIndex(0);
	}
	private void clearExtraForm() {
		fNameTField.setText("");
		fNameTField.setEnabled(false);

		nNameTField.setText("");
		nNameTField.setEnabled(false);

        newGenreTField.setText("");
        newGenreTField.setEnabled(false);

        
	}
	
	public void fillCombos(String sql,JComboBox<String> combo,int column) {
		int itemCount = combo.getItemCount();
		boolean isItemAdded=false;
		int rowCount = DBConnector.getAllModel(sql).getRowCount();
			for(int i=0;i<rowCount;i++) {
			String element =(String) DBConnector.getAllModel(sql).getValueAt(i,column);
			    	for(int x=0;x<itemCount;x++) {
				    	if(element==combo.getItemAt(x)) {
				     		isItemAdded=true;
				    	}
			    	}

			    	if(!isItemAdded) {
			     		combo.addItem(element);
			    	}
			    	isItemAdded=false;
			}
		}
	public void removeDeletedItemFromCombo(JComboBox<String> combo,String parameter) {
		for(int i=0;i<combo.getItemCount();i++) {
			if(parameter==combo.getItemAt(i)) {
				combo.removeItemAt(i);
				break;
			}
		}
	}

}//end class MyFrame
