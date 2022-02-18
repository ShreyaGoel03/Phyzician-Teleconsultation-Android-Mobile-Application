# Phyzician-Teleconsultation-Android-Mobile-Application

## Problem Statement
<p align = "justify">
The purpose of phyzician is to automate the existing manual system with the help of computer software and technologies. The primary goal is to keep track of doctor, appointment, patient, booking, and doctor schedule
information. It keeps track of all doctor-related information, such as fees and schedules. <b> This project aims to create application software that will decrease the amount of manual effort involved in maintaining
Doctors, Appointments, Doctor Fees, and Patients.</b> 

<p align = "justify">
One of the essential uses in telemedicine is teleconsultation, a method of communication over networks. Videoconferencing technology is used in real-time consultations, allowing medical specialists and clients
to connect. Both sites must interact and change photos and documents in real-time throughout the consultation. </p>

<p align = "justify">
Teleconsultation, which uses electronic information and communication technology, can provide a cost-effective healthcare approach used in clinics. A teleconsultation is a valuable tool for remote
consultations and discussion conferences. Teleconsultation can increase accessibility and quality of treatment and reduce expenses. The main
functions of the teleconsultation that are considered under a single application include:<br/><br/>
<b>❖ Doctors and Patient Login <br/>
❖ Appointment Booking & Cancellation <br/>
❖ In-app Prescription Form <br/>
❖ Chat with Doctors <br/>
❖ Health Tracker<br/>
❖ Admin Accessibility
</b> <br/>
</p>

### Overview: <br/>
<p align = "justify">
1. This app provides role-based login features for Admins, patients, and doctors. <br/>
2. Admins accounts will be created from the backend (i.e., generated by developers). One admin can
add multiple doctors in an application, record booking and cancelled appointments, manage the
payment status, and view chat details.<br/>
3. Further, Doctors can login, change passwords, update their details, edit their appointment slots,
and add prescriptions.<br/>
4. Patients can login with a phone number. They can view all doctor's and doctor's details based on
specialization, edit and book appointments, and download current and all previous prescriptions.<br/>
5. Patients can chat with doctors after booking an appointment, share images and video call links.<br/> 
6. Phyzician contains a feature Phyzihealth Health tracker, which will record all the basic patient
details, for example Body mass index, blood pressure, step count, and menstrual cycle.<br/> 
</p>

### Stakeholders
<p align = "justify">
1. <b> Administration </b> - Admin refers to the main head component of the application, which is an interface between the doctor and the patient.<br/>
2. <b> Doctors  </b> - The doctor is one of the application's stakeholders. The doctor has to set up their profile, choose the slots, check appointments and chat with the patients.<br/>
3. <b> Patients  </b> - The patient is one of the application's stakeholders. Any patient willing for a teleconsultation can log in to our application using its mobile number and can choose the doctor from the available speciality then, book an appointment from the open visiting hours of the doctor.<br/>
</p>

## ScreenShots

### Session Onboarding:
<p align = "justify"> Using material design Onboarding i.e Application Walkthrough - An initial hierarchy that creates meaning for our Application. Here, firstly, the welcome screen will be displayed for 2-3 sec which is created using the splash screen feature of the android studio.<p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154730818-f73b074d-d741-40bd-b64f-f93798f21957.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154730825-7e6237f2-9f92-4494-95e7-30bd22bca1a4.png" width=20% height=20%>     <img src="https://user-images.githubusercontent.com/43794593/154730828-7f5e9f81-aab4-49bf-b942-5122bd850e76.png" width=20% height=20%>     <img src="https://user-images.githubusercontent.com/43794593/154730832-74288c24-fc43-4b01-b4ad-6f63003fca3f.png" width=20% height=20%>
</p>

### Role based Login
<p align = "justify"> Role-based Login Activity is mainly used to redirect users to pages respectively. If the user is a doctor/admin, they will be able to login via doctors icon, and patients can login through patient icon. </p> 
<img src="https://user-images.githubusercontent.com/43794593/154730840-50ff5073-593b-493e-83c2-c5b0fede32b9.png" width=20% height=20%>

### Logout
<p align = "justify"> Logout feature is used to get out of the respective users' pages. It redirects to the login page again. </p> 
<img src="https://user-images.githubusercontent.com/43794593/154731854-33c3c711-2d73-4636-ad70-8e3c8d258431.png" width=20% height=20%> 

### Administration
<p align = "justify">  Admin refers to the main head component of the application, which is an interface between the doctor and the patient. The admin has the list of all the doctors available. It also has to verify the payments for the final confirmation of the appointments. It has the right to add a doctor to the available doctors in the application. Admin has the view of all the chats that are taking place between the patients with their respective doctors. The navigation drawer is present, which contains all the features available to the admin. </p>

#### Login
<p align = "justify"> Admins login credentials will be generated from the backend, containing login id and password. While
logging in for the first time, an email will get verified using the firebase authentication method, and they have the right to reset their password. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154749058-5da897b8-563f-4447-9b55-887afc8b4a36.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154749087-debbf5f4-b4f7-4b4d-ad73-dacba51d4f3a.png" width=20% height=20%> </p> 

#### All Doctors
<p align = "justify"> After login, the admin will see the list of all the available doctors who are currently providing the
teleconsultation. On clicking any doctor, the doctor's details are shown in another activity. The details
include today's availability, description of the doctor and consultation fees. A search bar is added, which
searches the doctors from the text input using the addTextChangedListener method of searchview. </p> 
<img src="https://user-images.githubusercontent.com/43794593/154749216-5d75be7e-58d3-4111-993e-f0df967a4426.png" width=20% height=20%>

#### Add Doctors 
<p align = "justify"> Admin has a right to add doctors at any time. The createUserWithEmailAndPassword() method is used to
register a new doctor, which requires two parameters: the email address and the password. Admin will
add doctors full name, email id, and password in the respected Edittext boxes to generate doctors'
credentials using the Register user button. After successful registration, the toast “Doctor added
successfully” will be visible. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154749293-ea776ecc-d76c-4084-9242-d8b0efcb9c72.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154749299-7bb79982-32d0-4c2a-ad95-5669a6f93296.png" width=20% height=20%> </p>

#### Chats
<p align = "justify">The admin can view the chats that are taking place between the patient and respective appointment
booked doctor. There is a drop-down list that contains the doctors, and after selecting the doctor, the list
of the chats of the patients (Name and Phone Number) are visible using recycler view. The admin can
click on any patient and view the conversations that have taken place. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154753203-be489bee-71a6-4c84-a6a3-88f93976026f.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154753212-64ff1f76-9e6e-4c73-98ae-a6cfa25a8b41.png" width=20% height=20%> </p>

#### All Appointments
<p align = "justify"> The admin can look at all the current and previous appointments between the patient and respective
doctor. There is a drop-down list that contains the doctors, and after selecting the doctor, the list of all the
previous and current appointments are visible in their respective fragments using recycler view. The
admin can see the patient name, contact number, date and time of the appointment, and its status. If the
payment is completed, the status is visible as "Payment Verified and Booked!" in green. Otherwise, if the
doctor cancels the appointment, it shows "Cancelled Appointment by Doctor!" in red. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154754138-b273eb22-86f4-490b-b055-178be84f32a6.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154754149-39738e36-0aa1-403c-954e-449a19990e2a.png" width=20% height=20%> </p>

#### Payment Details
<p align = "justify"> The payments status page is created with the help of the viewpager layout manager that allows the user to navigate across data pages by swiping left and right. Here, it consists of two pages, "completed" and
"upcoming," where all the details containing user name, mobile number, transaction id, doctor's name,
date, and time regarding payments will be reflected using the recycler view. With the help of
ItemTouchHelper class, on the right swipe of recycler view, a pop will appear asking, "Do you want to
mark this payment as done? or cancel the appointment? If the response is Yes, then payment details will
be added in the Completed section otherwise, the respected appointment will get cancelled, and the user
will get notified about the cancellation.</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154754273-f96eb10b-900b-412f-86f0-66853573953e.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154754281-e2f0d346-023c-40c9-ade2-f1a0de223b69.png" width=20% height=20%>        <img src="https://user-images.githubusercontent.com/43794593/154754292-dd3851a9-5e17-409a-9518-566161608cbd.png" width=20% height=25%>       <img src="https://user-images.githubusercontent.com/43794593/154756105-ca587639-d387-4a32-b187-d0b581cca110.png" width=20% height=20%> </p>

#### Feedbacks
<p align = "justify">The admin can look at all the doctor's feedback from the appointments. There is a drop-down list that
contains the doctors, and after selecting the doctor, the list of all the feedbacks are visible using recycler
view. The admin can see the patient name, contact number, date and time of the appointment, and with the
click of the particular view holder, the feedback received is visible. The feedback status and rating stars
are visible using the RatingBar. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154756196-869744ab-cd69-4460-bf68-81bb6237b0cb.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154756217-f183c73f-5c31-4162-b4a2-e8ab847a01e7.png" width=20% height=20%>        <img src="https://user-images.githubusercontent.com/43794593/154756240-f106ef05-bb8a-4aa7-8687-cdc476af61ab.png" width=20% height=20%></p>
<br/>

### Doctor
<p align = "justify">The doctor is one of the application's stakeholders. The doctor has to set up their profile, choose the slots, check appointments and chat with the patients. The doctor can upload and look at all the prescriptions of
the patient. The doctor can update its teleconsultation slots according to their availability. They can check
all the booked appointments and chat with the patients. </p> 

#### Login
<p align = "justify"> Doctors login credentials will be given by Admin, containing login id and password. While logging in for the first time, an email will get verified using
the firebase authentication method, and they have the right to reset their password.</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154756481-b752ad07-28dc-4b11-8992-409983525170.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154756490-52a7fd84-7ac5-4e98-b63b-65249f31ee67.png" width=20% height=20%>  </p>

#### Forgot/Update Password
<p align = "justify"> With the click of forgot password, the screen will appear containing edit text for
email id. With the reset button click, the user will receive a reset password email sent from Firebase
Console</p> 
<img src="https://user-images.githubusercontent.com/43794593/154756492-641214e9-c53c-4ff0-ae92-97feaf392667.png" width=20% height=20%>

#### View Profile
<p align = "justify"> If Doctor is logging in for the first time in the application, they have to update all the necessary details otherwise, there is an option to edit attributes in a profile section. The details to be added are Name,
Gender, Email, Speciality, Experience, Bio, Consultation Fees, Profile picture, and will be added in the
firebase. On the click of the Profile option, the profile of the doctor will be visible along with their image.</p>
<img src="https://user-images.githubusercontent.com/43794593/154756728-b2391cb4-a66a-49e0-ad0f-f0baed518d78.png" width=20% height=20%>

#### Update Profile
<p align = "justify">The doctor can update the details anytime by clicking on update details. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154756789-4baf2cee-59cb-4a47-9860-a1018568c63a.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154756799-b1df43a3-dde5-4c7a-9006-e8d5cea35af2.png" width=20% height=20%>  </p>

#### Scheduled Appointments
<p align = "justify"> All the confirmed appointments of the doctor will be displayed on the first page using the recycler view. The doctor can see the patient's name, contact number, date and time of the appointment, and status. A search bar is added, which searches the patients from the date input using the addTextChangedListener()
method of searchview. Further on the click of recycler view, respected details of patient get displayed.</p>
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154756926-e0e186f4-598d-4c76-bb8d-3a2f10e5d07c.png" width=20% height=20%>  </p>

#### My Appointments
<p align = "justify">The doctor can look at all the current and previous appointments. The details of all the previous and
current appointments are visible in their respective fragments using a recycler view. The doctor can see
the patient's name, contact number, date and time of the appointment, and its status. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154756984-42135608-7528-4b4f-a574-2356fc7ebac4.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154756996-6af77b05-1103-49d8-80bf-90d3c9949922.png" width=20% height=20%>  </p>

#### Your Slots
<p align = "justify"> The doctor can choose its visiting hours on respective dates for a week. The doctor can choose
from that drop-down and click on the select button. The doctor has first to choose the date, and then start and end times are visible. All the slots chosen are visible in the Your Chosen Slots section using the recycler view. The date, time and count of the booked slots by the patients are shown. The doctor can delete its chosen visiting hours by swiping the slot to the
right from the "Your Chosen Slots" section.</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154757039-d52fafa3-8a21-4bf2-aaca-b12b491b1889.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154757052-6923e841-7b7a-42e5-86f7-f3c3ea928f4e.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154757061-80c34f02-aaed-409e-9b07-ece6dec30237.png" width=20% height=20%>  </p>

#### Chats
<p align = "justify">The doctor can chat with the patients who have booked appointments with him. The list of the patients is
visible with their names and phone numbers. The doctor can click on any patient and initiate or reply to
the conversation. The chat window has a attach document/image button through which the doctor can
send or receive any image as chat. A send button sends the message typed by the doctor to the patient. </p>
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154760274-08d3a47b-d85c-426d-bd77-68813ff6ce60.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154753212-64ff1f76-9e6e-4c73-98ae-a6cfa25a8b41.png" width=20% height=20%> </p>

#### Prescriptions
<p align = "justify"> With the click of the upload prescription button, the screen will open where doctors can edit all the
details, and The same information will be reflected on the patient's side. Doctors can edit the patient's
name, gender, age, address, height, weight, instructions, consultation type, date, last lab report details,
Relevant points from history, Diagnosis information, examinations, and medications.  </p>
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154757520-1c9d92ac-0870-4b3b-8e47-e5e0a61ad153.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154757526-3e5621e2-ffc3-43b3-96bc-43302e73819a.png" width=20% height=20%> </p><br/>

### Patient
<p align = "justify"> The patient is one of the application's stakeholders. Any patient willing for a teleconsultation can log in to our application using its mobile number and receive OTP. The patient can choose the doctor from the available speciality and book an appointment from the open visiting hours of the doctor.</p> <br/>
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154757588-4964e71c-2076-45a7-a63a-0cb3d2691e0f.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154757599-c38faa53-9bfe-408d-885a-8bfcd25322ee.png" width=20% height=20%> </p>

#### Login
<p align = "justify"> The phone number authentication method is used on the patient's side, and the patient has to verify his
identity with his phone number. Here on the patient login page patient has to add his phone number in the
given edit text, After clicking the Get OTP button, Firebase will send an OTP to the number provided, and the patient has to input it in the following text field and then click the login button to confirm that the OTP is correct.</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154757608-d289423b-6864-4f52-bc6b-0166ba7e68b4.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154757620-4f5da234-51b7-47fd-a9ee-d26a07a36f13.png" width=20% height=20%> </p>

#### Our Doctors
<p align = "justify">After login, the patient can see the list of all the available doctors who are currently providing the
teleconsultation on our doctors' click. On clicking any doctor, the doctor's details are shown in another
activity.</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154757634-9eb679ec-2415-47a7-bdbf-a88c46be32c5.png" width=20% height=20%>  </p>

#### Find Doctors by Speciality
<p align = "justify"> After login, the patient can see the list of all the available specializations using the horizontal recycler view or search speciality in the grid recycler view. On clicking any doctor, the doctor's details are shown in another activity. The patient can click on any speciality, and the respective doctors are shown in the next activity. The patient can look at the profile of the doctors by clicking on the doctor list view.</p>
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154757646-ee3ca7dc-bba2-4bd9-901b-22f07151c617.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154757661-af91b4aa-15d7-43e6-be1e-c02bd5eebf33.png" width=20% height=20%> </p>

#### Know Your Doctor
<p align = "justify">Know your doctor screen contains all the details related to the doctor. The details include Name,
Specialized area of the doctor, today's availability, description of the doctor, contact details, and
consultation fees. The buttons All Prescription, Book Appointment and Chat are visible. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154757680-beea25df-1464-4ca1-861d-840edd6427e0.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154757689-73e289fd-25fb-42a5-8817-4d2d6ff7d032.png" width=20% height=20%> </p>

#### All Prescriptions
<p align = "justify">With the click of the all prescription button, the screen will open where The recycler view, adapter, and view holders present the Doctor's name, date, and time. If the patient is opening a prescription for the first time mandatory page to provide doctors, feedback will appear on clicking any prescription. Otherwise, all the details will be displayed</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154758084-7bddcc13-cf8d-4bf4-8c43-24a1488a86fd.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154758099-6c5c8a02-cbca-4105-9212-53e0968c2847.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154758113-ed58f38d-6cb3-4c9d-b1b4-52dad8ddfa04.png" width=20% height=20%> </p>

#### Book Appointment
<p align = "justify"> The booking appointment shows the doctor name, display picture and bio. The devs.mulham.horizontalcalendar.HorizontalCalendar has been used to display the calendar. The patient selects the dates by scrolling. The time slots are visible in the drop-down respective to the date selected. The patient has to enter their name and add any Questions for Doctor?. With the click of Pay Consultation Fees, the patient is redirected to another activity, where the doctor's consultation fees are visible. The patient can make the payment through the provided payment link and update the details with the Transaction ID. With the Book Appointment Button click, the appointment gets booked, and the final status is updated in the Your Appointments tab.</p>
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154758370-8d053720-125f-40a9-aafc-a8335626d390.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154758383-a05c4b10-72e2-4c5a-b5fe-a62557789b5d.png" width=20% height=20%>     <img src="https://user-images.githubusercontent.com/43794593/154758391-ea0e9bbc-41dc-47ff-bf0f-1808582511da.png" width=20% height=20%></p>

#### Your Appointments
<p align = "justify">The patient can look at all the current and previous appointments. The details of all the previous and
current appointments are visible in their respective fragments using a recycler view. The patient can see
the doctor's name, specialization, appointment date and time, transaction ID, payment status, and
appointment status. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154758406-de84ef5c-4c5e-46a1-9637-5fd491751b54.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154758556-4c4ba3fc-8c81-4b32-9049-416a265c4556.png" width=20% height=20%> </p>

#### Chats
<p align = "justify"> The patient can chat with the doctors who have booked appointments with him. The doctor's name and
image as a display picture will be visible to patients to initiate or reply to the conversation. The chat
window has a attach document/image button through which the patient can send or receive any image as
chat. A send button sends the message typed by the doctor to the patient. The date and time of the
message sent or received are also visible.</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154758598-8453ac87-e892-4dbc-9794-95ddc3240d5a.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154758607-cc4ff0aa-7da9-4c0d-9c38-15793c2413df.png" width=20% height=20%>      <img src="https://user-images.githubusercontent.com/43794593/154758710-d764326f-fabb-4eb4-b8ee-3af8d194a34e.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154758716-eb0da0fa-4f02-4a72-92ae-0ebb7b201cd7.png" width=20% height=20%> </p><br/>
<br/>

### PhyziHealth
<p align = "justify"> This feature is available on the Patient Side. It acts as a fitness tracker which records the BMI, Daily Steps, Blood Pressure and Menstrual Cycle. The patient can choose the PhyziHealth option available in
the Navigation drawer. This module helps in the maintenance of the physical health of the patient. The
patient must permit the ACTIVITY_RECOGNITION to track the activity using mobile sensors.</p>
<img src="https://user-images.githubusercontent.com/43794593/154758851-d9d17b1d-f5c7-49a0-96b5-a5079cd0e26c.png" width=20 height=20>
<br/>

#### BMI
<p align = "justify">The BMI is based on a person's height and weight. BMI = kg/m2, where kg represents a person's weight
in kilograms and m2 represents their height in meters squared. Here the user has to enter weight and height in the respected edit text fields, and with the click of calculating BMI button, As a result, BMI value and weight status will be displayed. </p>
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154759155-8794eafa-30aa-42e9-a49f-e6de25988afd.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154759057-5f711e2a-7f76-4aa9-8563-0612ef3c3e52.png" width=20% height=20%> </p>

#### Step Counter
<p align = "justify"> The patient can keep track of the daily steps with the distance travelled in km and calories burnt in cal. The horizontal calendar is visible at the top, pointing at the current date. The permission ACTIVITY_RECOGNITION is checked if it has been granted, then the step counter service is started in the background. The TYPE_STEP_COUNTER sensor is used, which registers the listener and updates the steps simultaneously as it tracks them using the Step sensor. The distance in cm is calculated as the product of 74*number of steps which are converted into kms. Then the calories burnt is calculated by the product of 0.04*number of steps. The steps, calories, and distance are visible in their edit text boxes.</p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154759068-8f562591-f7a6-450a-ac02-0d648feec440.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154759076-34c89a1d-a17a-40fb-879e-ad13e117d73e.png" width=20% height=20%> </p>

#### Blood Pressure
<p align = "justify">Phyzihealth has an option to record blood pressure to keep an eye on your health and is designed to
monitor and record both diastolic and systolic blood pressure, as well as your pulse rate. The user
selects the dates by scrolling and is picked up using the setCalendarListener() function. With the click of
dates on the calendar, the user can check previously recorded systolic and diastolic blood pressures.  </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154759322-ae4b8988-e470-4599-a826-6259be1e1f62.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154759328-1cdfd048-a126-44c6-b6a6-4dbd25dca95e.png" width=20% height=20%> <img src="https://user-images.githubusercontent.com/43794593/154759341-322ea8c4-d383-47ff-91b8-a3068577514b.png" width=20% height=20%></p>

#### Women’s Health
<p align = "justify"> Women's Health contains a menstruation tracker where savvi.rangedatepicker.CalendarPickerView() is
used for calendar. The click of duration will display the pop-up box to edit the cycle duration, and the upcoming date displays the next five months predicted dates. The home button redirects the patient to the home page. </p> 
<p float = "left">
<img src="https://user-images.githubusercontent.com/43794593/154759363-f96fe115-86d4-4d7f-9868-ee89bf5a88c0.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154759356-b11634e0-efff-4a55-be49-34e835adf33b.png" width=20% height=20%> <img src="https://user-images.githubusercontent.com/43794593/154759438-ec475216-1228-41c0-9380-87ac49e62642.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154759457-7d5fb9fb-0187-4580-8921-15d92512a379.png" width=20% height=20%>    <img src="https://user-images.githubusercontent.com/43794593/154759466-4203b31b-2f42-4667-9dc7-d7c9defe75ab.png" width=20% height=20%></p>





