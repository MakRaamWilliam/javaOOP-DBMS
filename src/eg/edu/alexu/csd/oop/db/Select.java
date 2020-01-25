package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;

public class Select extends GeneralDBMS{


	public Object[][] executeQuery(String query , Table table) throws SQLException {
	
		String regex [] = query.split(" ");
		Object[][] selItem = null;
		int fw=0;
		for (int i=0 ; i<regex.length ; i++) {
			if(regex[i].equalsIgnoreCase("where")) {
				fw=1;
			}
		}
		if (fw==0) {
			// select * from table
			if(regex[1].equalsIgnoreCase("*")) {
				selItem = new Object[table.getTablelist().get(0).size()][table.getNamesCol().size()]; 
				for(int i=0 ; i<table.getTablelist().get(0).size(); i++) {
					for(int j=0; j<table.getNamesCol().size(); j++) {
						selItem[i][j] = table.getTablelist().get(j).get(i);
					}
				}
			}
			
			else {
				// select column_name from table
				for(int i=0; i<table.getNamesCol().size(); i++) {
					if(regex[1].equalsIgnoreCase(table.getNamesCol().get(i))) {
							selItem = new Object[table.getTablelist().get(i).size()][1];
						for(int j=0; j<table.getTablelist().get(i).size(); j++) {
							selItem[j][0] = table.getTablelist().get(i).get(j);
						}
					}
				}
			}
		}
		
		
		else if(fw==1) {
			   int a=0 , b=0;
				// select column_name from table where column_name ?  'value' 
			   if( !"*".equals(regex[1])) {
				for(int i=0; i<table.getNamesCol().size() ; i++) {
					if(table.getNamesCol().get(i).equalsIgnoreCase(regex[1])) {
						for(int j=0 ;j<table.getNamesCol().size() ; j++) {
							if(table.getNamesCol().get(j).equalsIgnoreCase(regex[5])) {
								if(regex[7].contains("'")){
									if(table.getNameInt().contains(regex[1])) {
										throw new SQLException();
									}
									selItem = new Object [table.getTablelist().get(i).size()][1];
									String s = regex[7].replace("'", "");
								for(int k=0; k<table.getTablelist().get(j).size(); k++) {
									if(table.getTablelist().get(j).get(k).equalsIgnoreCase(s) && regex[6].equals("=")) {	
										selItem[a++][0] = table.getTablelist().get(i).get(k);
									}
								}}
								
								else {
									selItem = new Object[table.getTablelist().get(i).size()][1];
									String temb=query;
									temb=temb.replace(regex[0],"");
									temb=temb.replace(regex[1],"");
									temb=temb.replace(regex[2],"");
									temb=temb.replace(regex[3],"");
									temb=temb.replace(regex[4],"");
									temb=temb.replaceAll("\\s","");
							//		System.out.println(temb);
									for(int k=0; k<table.getTablelist().get(j).size(); k++) {
										if(temb.contains("=") && table.getTablelist().get(j).get(k).equalsIgnoreCase(temb.split("=")[1])) {
											selItem [a++][0] = table.getTablelist().get(i).get(k);
							//				System.out.println(table.getTablelist().get(i).get(k));
										}
										if(temb.contains(">") && Integer.parseInt(table.getTablelist().get(j).get(k)) > Integer.parseInt(temb.split(">")[1])) {
											selItem [a++][0] = table.getTablelist().get(i).get(k);
										}
										if(regex[6].equals("<") && Integer.parseInt(table.getTablelist().get(j).get(k)) < Integer.parseInt(temb.split("<")[1])) {
											selItem [a++][0] = table.getTablelist().get(i).get(k);
										}
									}
								}
							}	
						}
					}
				}
			   }
			   
			   else {
				// select * from table where column_name ? 'value'
				for(int i=0; i<table.getNamesCol().size() && regex[1].equals("*") ; i++) {
					if(table.getNamesCol().get(i).equalsIgnoreCase(regex[5])) {
						String temb=query;
						
						temb=temb.replace(regex[0],"");
						temb=temb.replace(regex[1],"");
						temb=temb.replace(regex[2],"");
						temb=temb.replace(regex[3],"");
						temb=temb.replace(regex[4],"");
						temb=temb.replaceAll("\\s","");
						if(temb.contains("'")) {
							if(table.getNameInt().contains(regex[1])) {
								throw new SQLException();
							}
							selItem = new Object[table.getTablelist().get(i).size()][table.getNamesCol().size()];
							String s = temb.replaceAll("'", "");
							for(int j=0; j<table.getTablelist().get(i).size(); j++) {
								b=0;
								if(temb.contains("=") && table.getTablelist().get(i).get(j).equalsIgnoreCase(s.split("=")[1])) {
									
									for(int k=0; k<table.getTablelist().size(); k++) {
										selItem[a][b++] = table.getTablelist().get(k).get(j);
										//System.out.println(table.getTablelist().get(j).get(k));
									}a++;
								}
								
							}
						}
						else {
							selItem = new Object[table.getTablelist().get(i).size()][table.getNamesCol().size()];
			//				System.out.println(table.getTablelist().get(i).size()+" "+table.getNamesCol().size());
							temb=query;
							temb=temb.replace(regex[0],"");
							temb=temb.replace(regex[1],"");
							temb=temb.replace(regex[2],"");
							temb=temb.replace(regex[3],"");
							temb=temb.replace(regex[4],"");
							temb=temb.replaceAll("\\s","");
				//			System.out.println(temb);
							for(int j=0; j<table.getTablelist().get(i).size(); j++) {
								b=0;
								if(temb.contains("=") && table.getTablelist().get(i).get(j).equalsIgnoreCase(temb.split("=")[1])) {
									for(int k=0; k<table.getNamesCol().size() ; k++) {
										selItem[a][b++] = table.getTablelist().get(k).get(j);
									}a++; 
								}
								if(temb.contains(">") && Integer.parseInt(table.getTablelist().get(i).get(j)) > Integer.parseInt(temb.split(">")[1])) {
									for(int k=0; k<table.getNamesCol().size(); k++) {
								//		System.out.println(table.getTablelist().get(k).get(j));
										selItem[a][b++] = table.getTablelist().get(k).get(j);
									}a++; 
								}
								if(temb.contains("<") && Integer.parseInt(table.getTablelist().get(i).get(j)) < Integer.parseInt(temb.split("<")[1])) {
									for(int k=0; k<table.getNamesCol().size(); k++) {
										selItem[a][b++] = table.getTablelist().get(k).get(j);
									}a++; 
								}
							}
						}
					}
				}}
		}int r=0;
		for(int m=0;m<selItem.length;m++) {
			for(int n=0; n<selItem[0].length;n++) {
			//	System.out.println(selItem[m][n]);
				if(selItem[m][n]!= null) {
		//			System.out.println(selItem[m][n]);
					r++;
				}
			}
		}if(selItem.length==0) {return null;} 
		Object[][] sel = new Object[r/selItem[0].length][selItem[0].length];
		for(int h=0; h<selItem[0].length; h++) {
			String s = ((String) selItem[0][h]);
			//System.out.println(s);
			if (s==null) {return null;}
			if( s.matches("-?\\d+") ) {
				for(int g=0; g<sel.length; g++) {
			//		System.out.println(selItem[g][h]);
					sel[g][h] =new Integer((String)selItem[g][h]);
				}
			}
			else if( selItem[0][h] != null) {
				for(int g=0; g<sel.length; g++) {
					sel[g][h] = new String(((String) selItem[g][h]).replace("'",""));
				}
			}
		}
		return sel;
	}
}
