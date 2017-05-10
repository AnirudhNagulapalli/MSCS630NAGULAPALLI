//
//  ViewController.swift
//  MyApp
//
//  Created by Anirudh Nagulapalli on 4/25/17.
//  Copyright © 2017 Anirudh Nagulapalli. All rights reserved.
//

import UIKit
import CryptoSwift




class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource,  UISearchDisplayDelegate {

    @IBOutlet weak var InformationLabel: UILabel!
    @IBOutlet weak var FirstNameLabel: UILabel!
    @IBOutlet weak var LastNameLabel: UILabel!
    @IBOutlet weak var DateOfBirthLabel: UILabel!
    @IBOutlet weak var EmailLabel: UILabel!
    @IBOutlet weak var StateLabel: UILabel!
    @IBOutlet weak var GenderLabel: UILabel!
    @IBOutlet weak var SSNnumberLabel: UILabel!
    
    @IBOutlet weak var FirstNameTextField: UITextField!
    @IBOutlet weak var LastNameTextField: UITextField!
    @IBOutlet weak var DateOfBirthTextField: UITextField!
    let datePicker = UIDatePicker()
    
    @IBOutlet weak var EmailTextField: UITextField!
    @IBOutlet weak var StateTextField: UITextField!
    var uipikerview = UIPickerView()
    
    @IBOutlet weak var GenderSegment: UISegmentedControl!
    @IBOutlet weak var SSNnumberTextField: UITextField!
    @IBOutlet weak var EncryptButton: UIButton!
    
    
    // For UIPicker - State
    var states = ["Alaska", "Alabama", "Arkansas", "American Samoa", "Arizona", "California", "Colorado", "Connecticut", "District of Columbia", "Delaware", "Florida", "Georgia", "Guam", "Hawaii", "Iowa", "Idaho", "Illinois", "Indiana", "Kansas", "Kentucky", "Louisiana", "Massachusetts", "Maryland", "Maine", "Michigan", "Minnesota", "Missouri", "Mississippi", "Montana", "North Carolina", "North Dakota", "Nebraska", "New Hampshire", "New Jersey", "New Mexico", "Nevada", "New York", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Virginia", "Virgin Islands", "Vermont", "Washington", "Wisconsin", "West Virginia", "Wyoming"]
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return states.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return states[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.StateTextField.text = self.states[row]
        self.view.endEditing(false)
    }
    
    // For Date Picker - Date
    
    func createDatePicker() {
        // For toolbar
        let toolbar = UIToolbar()
        toolbar.sizeToFit()
        
        // For Bar button item
        let doneButton = UIBarButtonItem(barButtonSystemItem: .done, target: nil, action: #selector(donePressed))
        toolbar.setItems([doneButton], animated: false)
        
        DateOfBirthTextField.inputAccessoryView = toolbar
        
        // assign date picker to text field
        DateOfBirthTextField.inputView = datePicker
        
        // Format for picker
        datePicker.datePickerMode = .date
        
    }
    
    // For 'Done'
    func donePressed() {
        // For formatting date
        let dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .long
        dateFormatter.timeStyle = .none
        
        DateOfBirthTextField.text = dateFormatter.string(from: datePicker.date)
        self.view.endEditing(true)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        uipikerview.delegate = self
        uipikerview.dataSource = self
        StateTextField.inputView = uipikerview
        
        createDatePicker()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


    @IBAction func encryptButtonTapped(_ sender: UIButton) {
        let userFirstName = FirstNameTextField.text;
        let userLastName = LastNameTextField.text;
        let userDateOfBirth = DateOfBirthTextField.text;
        let userEmail = EmailTextField.text;
        let userState = StateTextField.text;
        let userGender = GenderSegment.selectedSegmentIndex;
        let userSSNnumber = SSNnumberTextField.text;
        
//        // Check for empty fields
//        if((userFirstName?.isEmpty)! || (userLastName?.isEmpty)! || (userDateOfBirth?.isEmpty)! || (userEmail?.isEmpty)! || (userState?.isEmpty)! || (userSSNnumber?.isEmpty)!) {
//            
//            // Display alert message
//            displayMyAlertMessage(userMessage: "All fields are required");
//            return;
//        }
//        
//        // Store Data
//        UserDefaults.standard.set(userFirstName, forKey: "userFirstName");
//        UserDefaults.standard.set(userLastName, forKey: "userLastName");
//        UserDefaults.standard.set(userDateOfBirth, forKey: "userDateOfBirth");
//        UserDefaults.standard.set(userEmail, forKey: "userEmail");
//        UserDefaults.standard.set(userState, forKey: "userState");
//        UserDefaults.standard.set(userSSNnumber, forKey: "userSSNnumber");
//        UserDefaults.standard.synchronize();
//        
//        // Display alert message with confirmation
//        var myAlert = UIAlertController(title:"Alert", message:"Information is entered successfully", preferredStyle:UIAlertControllerStyle.alert);
//        
//        let okAction = UIAlertAction(title:"Ok", style:UIAlertActionStyle.default){ action in
//            self.dismiss(animated: true, completion: nil);
//        }
//        
//        myAlert.addAction(okAction);
//        self.present(myAlert, animated:true, completion:nil)
    
    
        let data = Data(bytes: userSSNnumber)
        //let bytes = data.bytes
        
        let bytes = Array<UInt8>(hex:data)
        let hex = bytes.toHexString()
        
        
//        // Hashing
//        let bytes:Array<UInt8> = data
//        let digest = input.md5()
//        let digest = Digest.md5(bytes)
        
//        let hash = data.md5()
        let hash = data.sha1()
        
        //Calculating CRC
        bytes.crc16
        data.crc16
        
//        // Using HMAC
//        try HMAC(key: key, variant: .sha256).authenticate(bytes)

        
        let key: Array<UInt8> = [0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F]
        let iv: Array<UInt8> = AES.randomIV(AES.blockSize)
        
        do {
            let encrypted = try AES(key: key, iv: iv, blockMode: .CBC, padding: PKCS7()).encrypt(input)
            let decrypted = try AES(key: key, iv: iv, blockMode: .CBC, padding: PKCS7()).decrypt(encrypted)
        } catch {
            print(error)
        }
        
    }
    
//    func displayMyAlertMessage(userMessage:String) {
//        var myAlert = UIAlertController(title:"Alert", message:userMessage, preferredStyle:UIAlertControllerStyle.alert);
//
//        
//        let okAction = UIAlertAction(title:"Ok", style:UIAlertActionStyle.default, handler:nil);
//        
//        myAlert.addAction(okAction);
//        
//        self.present(myAlert, animated:true, completion:nil);
//    }
    
    
    
}

