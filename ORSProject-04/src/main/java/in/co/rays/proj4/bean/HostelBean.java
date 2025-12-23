package in.co.rays.proj4.bean;

public class HostelBean extends BaseBean {

	private String name;
	private String type;
	private long capacity;
	private String description;
	private long collegeId;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getType() {
		return type;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		 return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}


}
