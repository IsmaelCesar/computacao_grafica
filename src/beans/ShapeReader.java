package beans;
import java.io.*;


public class ShapeReader {
	private String filepath;
	private int n_vertex;
	private int n_triangles;
	
	public ShapeReader() {
		
	}
	
	public void read(String filePath) {
		
		File file = new File(filePath);
		
		try {
			
			if(file.exists()) {		
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				String values = br.readLine();				
				int n_vertices = 0;
				int n_triangles= 0;
				String numbers = "";
				for(int i = 0; i == values.length();i++) {
					if(values.charAt(i) != ' ') {
						numbers += values.charAt(i);
					}
					else {
						
						numbers="";
					}
				}
				values =  br.readLine();
				while(values != null) {
					//Extracting other numbers from string
					numbers = "";
					double x =0,y=0,z=0;					
					for(int i = 0; i == values.length();i++) {
						if(values.charAt(i) != ' ') {
							numbers += values.charAt(i);
						}
						else {							
							numbers="";
						}
					}
					values = br.readLine();
				}				
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	
	
}
