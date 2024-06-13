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
import entity.enums.Fuel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateCarView extends JFrame {


    private JPanel container;
    private JComboBox <ComboItem>cmb_model;
    private JComboBox<Car.Color> cmb_color;
    private JTextField txt_km;
    private JTextField txt_plate;
    private JTextField txt_price;
    private JButton saveButton;
    private Car car;
    private ICarService carService;
    private IModelService modelService;


    public AdminCreateCarView(){
        this.add(container);
        this.car = new Car();
        this.modelService = new ModelManager();
        FrameHelper.setupFrame(this, 400, 400, "Rent A Car");
        carService= new CarManager();


        for (Model model : this.modelService.getAll())
        {
            this.cmb_model.addItem(model.getComboItem());
        }
        this.cmb_color.setModel(new DefaultComboBoxModel<>(Car.Color.values()));


        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedModel = (ComboItem) cmb_model.getSelectedItem();
                car.setModel_id(selectedModel.getKey());
                car.setColor((Car.Color) cmb_color.getSelectedItem());
                car.setPlate(txt_plate.getText());
                car.setKm(Integer.parseInt(txt_km.getText()));
                car.setDaily_price(Integer.parseInt(txt_price.getText()));

                SuccessInformationMessage carCreated = carService.create(car);


                carCreated.showMessageDialog();
                dispose();
            }
        });
    }
}
