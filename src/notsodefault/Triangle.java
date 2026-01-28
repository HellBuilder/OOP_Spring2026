package notsodefault;

public class Triangle{
	
	private int Size_Tri;
	
	public Triangle(int Size_Tri) {
        this.Size_Tri = Size_Tri;
    }
	
	public void ToString() {
		for(int i = 1; i <= Size_Tri; i++) {
			for(int j = 1; j < i; j++) {
				System.out.print("[*]");
			}
			System.out.println("[*]");
		}
	}
}