package view.Admin.Brand;

import business.Concrete.BrandManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.FrameHelper;
import business.Services.IBrandService;
import entity.Brand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminEditBrandView extends JFrame {


    private JPanel container;
    private JTextField txt_brandName;
    private JButton btn_save;
    private Brand brand;
    private IBrandService brandService;

    public AdminEditBrandView(Brand brand, AdminBrandHomeView brandHomeView) {

        this.add(container);
        this.brand = new Brand();
        this.brandService = new BrandManager();
        FrameHelper.setupFrame(this, 300, 200, "Rent A Car");
        txt_brandName.setText(brand.getName());

        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brand.setName(txt_brandName.getText());
                SuccessInformationMessage brandUpdated = brandService.update(brand);
                brandUpdated.showMessageDialog();
                dispose();
            }
        });
    }


}
