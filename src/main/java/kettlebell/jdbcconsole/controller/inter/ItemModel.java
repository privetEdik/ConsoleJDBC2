package kettlebell.jdbcconsole.controller.inter;

public interface ItemModel {
	public String getById(int id);
	public String getAll();
	public int remove(int id);	
}
