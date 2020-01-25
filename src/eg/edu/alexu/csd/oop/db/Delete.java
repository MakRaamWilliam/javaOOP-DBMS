package eg.edu.alexu.csd.oop.db;

import java.util.ArrayList;

public class Delete {
	xml xfile=new xml();
	Schema sfile=new Schema();
	int result=0;
	int index=-1;
	boolean isint=false;
	boolean where=false;
	String query;
	ArrayList<Integer>rows;
	ArrayList<ArrayList<String>> updated;
	Table t;
	
	public Delete(String query, ArrayList<ArrayList<String>> u ,ArrayList<Integer>r, Table table ) {
		this.query=query; 
		 this.updated = new ArrayList<ArrayList<String>>(); updated=u;
		this.rows= new ArrayList<Integer>(); rows=r;
		this.t = new Table(table);
	
	}
    public int getdelete(String dir,String namet) {
		int result=0;
	
		String regex [] = query.split(" ");
		int flag=0;
		String name=regex[2];
		if(name.contains("(")) {
			name=name.split("\\(")[0];
		}
		updated.addAll(t.getTablelist());
		for(int i=0;i<regex.length;i++) {
			if(regex[i].equalsIgnoreCase("where")) {
				flag=1;
			}
		}
		if(flag==0) {      //there is no condition then remove all rows
			result=updated.get(0).size();
			for(int i=0; i<updated.size();i++) {
				updated.get(i).clear();
			}
			xfile.removeXml(dir, namet);
			sfile.removeXsd(dir, namet);
		    t.setTableList(updated);
	       
		}
		else {
			String temb=query;
			temb=temb.replace(regex[0],"");
			temb=temb.replace(regex[1],"");
			temb=temb.replace(name,"");
			temb=temb.replaceAll("(W|w)(H|h)(E|e)(R|r)(E|e)","");
			temb=temb.replaceAll("\\s","");
			String cond[];
			if(temb.contains("<>")) {
				index=-1;
				cond=temb.split("<>");
				
				for(int j=0;j<t.getNamesCol().size();j++) {  
					if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
						index=j;
					}
				}
				if(index==-1) {
					return 0;
				}
				for(int j=0;j<t.getNameInt().size();j++) {
					if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
						isint=true;
					}
				}
				if(isint &&cond[1].contains("'")) {
					return 0;
				}
				if(isint&&!cond[1].contains("'")) {
					for(int j=0;j<t.getTablelist().get(index).size();j++) {
						if(Integer.parseInt(t.getTablelist().get(index).get(j))!=Integer.parseInt(cond[1])) {
							rows.add(j);
						}
					}
				}
				else if(!isint &&!cond[1].contains("'")) {
					return 0;
				}
				else {
					cond[1]=cond[1].replaceAll("'","");
					for(int j=0;j<t.getTablelist().get(index).size();j++) {
						if(!(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1]))) {
							rows.add(j);
						}
					}
				}
			}
			
			else if(temb.contains("<=")) {
				index=-1;
				cond=temb.split("<=");
				
				if(cond[1].contains("'")) {
					return 0;
				}
				
				for(int j=0;j<t.getNamesCol().size();j++) {  
					if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
						index=j;
					}
				}
				if(index==-1) {
					return 0;
				}
				
				for(int j=0;j<t.getNameInt().size();j++) {
					if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
						isint=true;
					}
				}
				if(isint==false) {
					return 0;
				}
				for(int j=0;j<t.getTablelist().get(index).size();j++) {
					if(Integer.parseInt(t.getTablelist().get(index).get(j))<=Integer.parseInt(cond[1])) {
						rows.add(j);
					}
				}
			}
			else if(temb.contains(">=")) {
				index=-1;
				cond=temb.split(">=");
				
				if(cond[1].contains("'")) {
					return 0;
				}
				
				for(int j=0;j<t.getNamesCol().size();j++) {  
					if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
						index=j;
					}
				}
				if(index==-1) {
					return 0;
				}
				
				for(int j=0;j<t.getNameInt().size();j++) {
					if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
						isint=true;
					}
				}
				if(isint==false) {
					return 0;
				}
				for(int j=0;j<t.getTablelist().get(index).size();j++) {
					if(Integer.parseInt(t.getTablelist().get(index).get(j))>=Integer.parseInt(cond[1])) {
						rows.add(j);
					}
				}
			}
			
			else if(temb.contains("=")) {
				index=-1;
				cond=temb.split("=");
				for(int j=0;j<t.getNamesCol().size();j++) {  
					if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
						index=j;
						
					}
				}
				if(index==-1) {
					return 0;
				}
				for(int j=0;j<t.getNameInt().size();j++) {
					if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
						isint=true;
					}
				}
				if(isint &&cond[1].contains("'")) {
					return 0;
				}
				if(isint&&!cond[1].contains("'")) {
					for(int j=0;j<t.getTablelist().get(index).size();j++) {
						if(Integer.parseInt(t.getTablelist().get(index).get(j))==Integer.parseInt(cond[1])) {
							rows.add(j);
						}
					}
				}
				else if(!isint &&!cond[1].contains("'")) {
					return 0;
				}
				else {
					cond[1]=cond[1].replaceAll("'","");
					for(int j=0;j<t.getTablelist().get(index).size();j++) {
						if(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1])) {
							rows.add(j);
						}
					}
				}
			}
			else if(temb.contains("<")) {
				index=-1;
				cond=temb.split("<");
				if(cond[1].contains("'")) {   //check if target value is string
					return 0;
				}
				
				for(int j=0;j<t.getNamesCol().size();j++) {  //check the column name
					if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
						index=j;
					}
				}
				if(index==-1) {
					return 0;
				}
				
				for(int j=0;j<t.getNameInt().size();j++) {
					if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
						isint=true;
					}
				}
				if(isint==false) {
					return 0;
				}
				for(int j=0;j<t.getTablelist().get(index).size();j++) {
					if(Integer.parseInt(t.getTablelist().get(index).get(j))<Integer.parseInt(cond[1])) {
						rows.add(j);
					}
				}
				

			}
			else if(temb.contains(">")) {
				index=-1;
				cond=temb.split(">");
				if(cond[1].contains("'")) {
					return 0;
				}
				
				for(int j=0;j<t.getNamesCol().size();j++) {  
					if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
						index=j;
					}
				}
				if(index==-1) {
					return 0;
				}
				
				for(int j=0;j<t.getNameInt().size();j++) {
					if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
						isint=true;
					}
				}
				if(isint==false) {
					return 0;
				}
				for(int j=0;j<t.getTablelist().get(index).size();j++) {
					if(Integer.parseInt(t.getTablelist().get(index).get(j))>Integer.parseInt(cond[1])) {
						rows.add(j);
					}
				}
			}
			
			if(rows.size()==0) {
				return 0;
			}
			else {
				result=rows.size();
				for(int l=0;l<result;l++) {
					for(int j=0;j<t.getNamesCol().size();j++) {
						ArrayList<String> newValue=new ArrayList<String>();
						newValue=updated.get(j);
						index=rows.get(l);
						newValue.remove(index);
						updated.remove(j);
						updated.add(j,newValue);
						xfile.updateXml(dir, namet, j, newValue);
						sfile.insertXsd(dir, namet, j, newValue);
					}
					for(int j=0;j<result;j++) {
						int k=rows.get(j)-1;
						rows.remove(j);
						rows.add(j, k);
					}
				}
				t.setTableList(updated);
				
			}
		}
		
 


return result;
	}
	
}
