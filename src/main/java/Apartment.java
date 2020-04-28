public class Apartment {
    @Id
    private int id;
    private String district;
    private String address;
    private int area;
    private int numberOfRooms;
    private int price;

    public Apartment() {}

    public Apartment(String district, String address, int area, int numberOfRooms, int price) {
        this.district = district;
        this.address = address;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.price = price;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public int getArea() {
        return area;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getPrice() {
        return price;
    }

}
