package dao.Concrete;

import core.Db;
import dao.Abstract.ICarDal;
import dao.Abstract.IModelDal;
import entity.Car;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDal implements ICarDal {

    private final Connection conn;
    private IModelDal modelDal;

    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.car ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.car (model_id, color, km, plate, daily_price) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE public.car SET model_id = ?, color = ?, km = ?, plate = ?, daily_price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.car WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.car WHERE id = ?";


    public CarDal() {
        this.conn = Db.getInstance();
        this.modelDal = new ModelDal();
    }
    @Override
    public ArrayList<Car> getAll() {
        ArrayList<Car> cars = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                cars.add(extractCar(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public boolean create(Car car) {
        return executeUpdate(INSERT_QUERY, car);
    }

    @Override
    public boolean update(Car car) {
        return executeUpdate(UPDATE_QUERY, car);
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement pr = conn.prepareStatement(DELETE_QUERY)) {
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Car getByid(int id) {
        Car car = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                car = extractCar(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public ArrayList<Car> selectByQuery(String query) {
        ArrayList<Car> carArrayList = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                carArrayList.add(this.extractCar(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return carArrayList;
    }

    private boolean executeUpdate(String query, Car car) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, car.getModel_id());
            pr.setString(2, car.getColor().toString());
            pr.setInt(3, car.getKm());
            pr.setString(4, car.getPlate().toString());
            pr.setInt(5, car.getDaily_price());
            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(6, car.getId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private Car extractCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setModel_id(rs.getInt("model_id"));
        car.setColor(Car.Color.valueOf(rs.getString("color")));
        car.setKm(rs.getInt("km"));
        car.setPlate(rs.getString("plate"));
        car.setDaily_price(rs.getInt("daily_price"));
        car.setModel(this.modelDal.getByid(rs.getInt("model_id")));

        return car;
    }
}
