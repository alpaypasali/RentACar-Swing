package dao.Concrete;

import core.Db;
import dao.Abstract.IBrandDal;
import dao.Abstract.IModelDal;
import entity.Model;
import entity.enums.Fuel;
import entity.enums.Gear;
import entity.enums.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelDal implements IModelDal {
    private final Connection conn;
    private  final IBrandDal brandDal;

    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.model ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.model (name, fuel, gear, type, year, brand_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE public.model SET name = ?, fuel = ?, gear = ?, type = ?, year = ?, brand_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.model WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.model WHERE id = ?";

    public ModelDal() {
        this.conn = Db.getInstance();
        brandDal = new BrandDal();
    }

    @Override
    public ArrayList<Model> getAll() {
        ArrayList<Model> models = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                models.add(extractUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return models;
    }

    @Override
    public boolean create(Model model) {
        return executeUpdate(INSERT_QUERY, model);
    }

    @Override
    public boolean update(Model model) {
        return executeUpdate(UPDATE_QUERY, model);
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
    public Model getByid(int id) {
        Model model = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                model = extractUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    private boolean executeUpdate(String query, Model model) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setString(1, model.getName());
            pr.setString(2, model.getFuel().toString());
            pr.setString(3, model.getGear().toString());
            pr.setString(4, model.getType().toString());
            pr.setInt(5, model.getYear());
            pr.setInt(6, model.getBrand_id());
            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(7, model.getId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Model extractUser(ResultSet rs) throws SQLException {
        Model model = new Model();
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setFuel(Fuel.valueOf(rs.getString("fuel")));
        model.setGear(Gear.valueOf(rs.getString("gear")));
        model.setType(Type.valueOf(rs.getString("type")));
        model.setYear(rs.getInt("year"));
        model.setBrand_id(rs.getInt("brand_id"));
        model.setBrand(this.brandDal.getByid(rs.getInt("brand_id")));
        return model;
    }
}
