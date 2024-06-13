package view.Admin.Model;


import business.Concrete.BrandManager;
import business.Concrete.ModelManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.ComboItem;
import business.Helpers.FrameHelper;
import business.Services.IBrandService;
import business.Services.IModelService;
import entity.Brand;
import entity.Model;
import entity.enums.Fuel;
import entity.enums.Gear;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminEditModelView extends JFrame {


    private JPanel container;
    private JComboBox cmb_brand;
    private JTextField txt_name;
    private JTextField txt_year;
    private JComboBox<entity.enums.Type> cmb_type;
    private JComboBox<Fuel> cmb_fuel;
    private JComboBox<Gear> cmb_gear;
    private JButton btn_Save;
    private Model model;
    private IModelService modelService;
    private IBrandService brandService;

    public AdminEditModelView(Model model, AdminModelHomeView adminModelHomeView) {
        this.add(container);
        this.model=model;
        this.modelService = new ModelManager();
        FrameHelper.setupFrame(this, 300, 500, "Rent A Car");
        brandService= new BrandManager();

        for (Brand brand : this.brandService.getAll())
        {
            this.cmb_brand.addItem(new ComboItem(brand.getId(),brand.getName()));
        }
        this.cmb_fuel.setModel(new DefaultComboBoxModel<>(Fuel.values()));
        this.cmb_type.setModel(new DefaultComboBoxModel<>(entity.enums.Type.values()));
        this.cmb_gear.setModel(new DefaultComboBoxModel<>(Gear.values()));


        this.txt_year.setText(String.valueOf(this.model.getYear()));
        this.txt_name.setText(this.model.getName());
        this.cmb_fuel.getModel().setSelectedItem(this.model.getFuel());
        this.cmb_type.getModel().setSelectedItem(this.model.getType());
        this.cmb_gear.getModel().setSelectedItem(this.model.getGear());
        ComboItem defaultBrand = new ComboItem(this.model.getBrand().getId(),this.model.getBrand().getName());
        this.cmb_brand.getModel().setSelectedItem(defaultBrand);
        this.btn_Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedBrand = (ComboItem) cmb_brand.getSelectedItem();

                model.setYear(Integer.parseInt(txt_year.getText()));

                model.setName(txt_name.getText());
                model.setBrand_id(selectedBrand.getKey());
                model.setType((entity.enums.Type) cmb_type.getSelectedItem());
                model.setFuel((Fuel) cmb_fuel.getSelectedItem());
                model.setGear((Gear) cmb_gear.getSelectedItem());

                SuccessInformationMessage modelUpdated = modelService.update(model);


                modelUpdated.showMessageDialog();
                dispose();
            }
        });
    }
}