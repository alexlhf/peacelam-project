COMP5134: Project (2016 Spring)

A. Open the source code in Eclipse and run the program:
1. Use browser to access the hyper link of the project from git.txt

2. Click Download ZIP to download compressed project file from GitHub

3. Save the zip file to a temporary folder, then uncompress the zip file. Then, rename the parent folder
   from peacelam-project-master to COMP5134_Project

4. Select File->Import->General->Existing Projects to Workspace in Eclipse

5. Select root directory

6. Browse COMP5134_Project from the temporary folder

7. Click "Finish"

8. The projects source codes under the project's Workspace contains the following folders
src/com/polyu.comp5134.domain/data  - the elementary data of Staff and Leave applications.
src/com/polyu.comp5134.domain/model - the model to update and retrieval the data.
src/com/polyu.comp5134.domain/util - sequence generator for Staff ID and Leave ID and Helper Class for managing staffs.
src/com/polyu/comp5134/ui/view	- all the source codes related to UI views

9. Open a command prompt and change directory to the project's HOME directory.

10. Execute "run.bat" or java -jar dist\project.jar in the project's HOME folder to run the program.

B. Managing the system:
1. In the MainView GUI, click Login and then enter username/password: director/123456. There are three users were created by default,
namely director who is the Director, Mary who is system admin and Peter who is normal staff. Their passwords are 123456.

2. Only system admin role and director role are allowed to create or delete staff.

3. Any staff except director can use My Leaves view to apply for a leave from date X to date Y.

4. Once a staff has applied for a leave, her supervisor can use Approve Leave view to see the staff's leave applications and has the options to
"endorse" and "decline". 

i) If her supervisor endorses the leave, the leave will pass to her supervisor for further endorsement. Until all 
the direct/indirect supervisor of the staff has endorsed, the staff will see her result about her successful approval for her leave.

ii) If anyone in the process declines the leave application, the staff will be notified and the leave application does not further pass up.

5. The finalresult of the staff's leave applications will be displayed in My Leaves view.

