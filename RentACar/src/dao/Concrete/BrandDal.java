package dao.Concrete;

import core.Db;
import dao.Abstract.IBrandDal;
import entity.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDal implements IBrandDal {

    private final Connection conn;

    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.brand ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.brand (name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE public.brand SET name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.brand WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.brand WHERE id = ?";

    public BrandDal() {
        this.conn = Db.getInstance();
    }
    @Override
    public ArrayList<Brand> getAll() {
        ArrayList<Brand> brands = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                brands.add(extractBrand(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    @Override
    public boolean create(Brand brand) {
        return executeUpdate(INSERT_QUERY, brand);
    }

    @Override
    public boolean update(Brand brand) {
        return executeUpdate(UPDATE_QUERY, brand);
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
    public Brand getByid(int id) {
        Brand brand = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                brand = extractBrand(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brand;
    }
    private boolean executeUpdate(String query, Brand brand) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setString(1, brand.getName());
            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(2, brand.getId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Brand extractBrand(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getInt("id"));
        brand.setName(rs.getString("name"));

        return brand;
    }
}