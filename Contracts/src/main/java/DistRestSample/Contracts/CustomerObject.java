package DistRestSample.Contracts;

import java.util.Collection;

import com.google.gson.Gson;

public class CustomerObject {
	public final long Id;
	public final String FirstName;
	public final String LastName;
	public final String Birthday;
	
	public CustomerObject(long id, String firstName, String lastName, String birthday) {
		Id = id;
		FirstName = firstName;
		LastName = lastName;
		Birthday = birthday;
	}
	
	public static boolean Equals(CustomerObject a, CustomerObject b) {
		if(a == null && b == null) {return true;}
		if(!(a == null || b == null)) { return false;}
		
		return 
				(a.Id == b.Id) &&
				(a.FirstName.compareTo(b.FirstName) == 0) &&
				(a.LastName.compareTo(b.LastName) == 0) &&
				(a.Birthday.compareTo(b.Birthday) == 0); 
	}
	
	public static CustomerObject FromJson(String json) {
		Gson gson = new Gson();
		CustomerObject instance = gson.fromJson(json, CustomerObject.class);
		return instance;
	}
	
	public static String ToJson(Collection<CustomerObject> objects) {
		CustomerObject[] allArray = (CustomerObject[]) objects.toArray();
		String json = new Gson().toJson(allArray);
		return json;
	}
	
	public String ToJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
