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

public class AdminCreateModelView extends  JFrame {
    private JComboBox cmb_brand;
    private JTextField txt_name;
    private JTextField txt_year;
    private JComboBox cmb_type;
    private JComboBox cmb_fuel;
    private JComboBox cmb_gear;
    private JButton btn_Save;
    private JPanel container;
    private Model model;
    private IModelService modelService;
    private IBrandService brandService;


    public AdminCreateModelView(){
        this.add(container);
        this.model = new Model();
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


        btn_Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedBrand = (ComboItem) cmb_brand.getSelectedItem();

                model.setYear(Integer.parseInt(txt_year.getText()));

                model.setName(txt_name.getText());
                model.setBrand_id(selectedBrand.getKey());
                model.setType((entity.enums.Type) cmb_type.getSelectedItem());
                model.setFuel((Fuel) cmb_fuel.getSelectedItem());
                model.setGear((Gear) cmb_gear.getSelectedItem());

                SuccessInformationMessage modelUpdated = modelService.create(model);


                modelUpdated.showMessageDialog();
                dispose();
            }
        });
    }
}
