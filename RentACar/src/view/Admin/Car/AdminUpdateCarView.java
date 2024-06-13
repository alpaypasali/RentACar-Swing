package view.Admin.Car;


import business.Concrete.CarManager;
import business.Concrete.ModelManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.ComboItem;
import business.Helpers.FrameHelper;
import business.Services.ICarService;
import business.Services.IModelService;
import entity.Car;
import entity.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUpdateCarView extends JFrame {
    private  Car car;
    private JPanel container;
    private JComboBox cmb_model;
    private JComboBox cmb_color;
    private JTextField txt_km;
    private JTextField txt_plate;
    private JTextField txt_price;
    private JButton saveButton;
    private ICarService carService;
    private IModelService modelService;


    public AdminUpdateCarView(Car car , AdminCarHomeView adminCarHomeView){
        this.add(container);
        this.car = car;
        this.modelService = new ModelManager();
        FrameHelper.setupFrame(this, 400, 400, "Rent A Car");
        carService= new CarManager();

        for (Model model : this.modelService.getAll())
        {
            this.cmb_model.addItem(new ComboItem(model.getId(),model.getName()));
        }
        this.cmb_color.setModel(new DefaultComboBoxModel<>(Car.Color.values()));


        this.txt_price.setText(String.valueOf(this.car.getDaily_price()));
        this.txt_km.setText(String.valueOf(this.car.getKm()));
        this.txt_plate.setText(this.car.getPlate());

        this.cmb_color.getModel().setSelectedItem(this.car.getColor());
        ComboItem defaultBrand = new ComboItem(this.car.getModel().getId(),this.car.getModel().getName());
        this.cmb_model.getModel().setSelectedItem(defaultBrand);

        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedModel = (ComboItem) cmb_model.getSelectedItem();
                car.setModel_id(selectedModel.getKey());
                car.setColor((Car.Color) cmb_color.getSelectedItem());
                car.setPlate(txt_plate.getText());
                car.setKm(Integer.parseInt(txt_km.getText()));
                car.setDaily_price(Integer.parseInt(txt_price.getText()));

                SuccessInformationMessage carCreated = carService.update(car);


                carCreated.showMessageDialog();
                dispose();
            }
        });

    }
}
