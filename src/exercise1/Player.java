package exercise1;

import java.util.List;

public class Player {
	private final String firstName;
	private final String lastName;
	private final String address;
	private final String postalCode;
	private final String province;
	private final String phoneNumber;
	private final List<String> gamesPlayed;
	
	public Player(String firstName, String lastName, String address,
			String postalCode, String province, String phoneNumber, List<String> listOfGames) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.province = province;
		this.phoneNumber = phoneNumber;
		gamesPlayed = listOfGames;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getProvince() {
		return province;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public List<String> getGamesPlayed() {
		return gamesPlayed;
	}
	
}
