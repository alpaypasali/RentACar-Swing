package view.Admin.Brand;

import business.Concrete.BrandManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.FrameHelper;
import business.Services.IBrandService;
import entity.Brand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateBrandView extends JFrame {

    private JPanel container;
    private JTextField txt_brandName;
    private JButton Btn_save;
    private IBrandService brandService;
    private Brand brand;

    public AdminCreateBrandView(ActionListener brandHomeView) {
        this.add(container);
        this.brandService = new BrandManager();
        this.brand = new Brand();
        FrameHelper.setupFrame(this, 300, 200, "Rent A Car");


        Btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brand.setName(txt_brandName.getText());
                SuccessInformationMessage createdBrand = brandService.create(brand);
                createdBrand.getMessage();
                dispose();
            }
        });
    }
}