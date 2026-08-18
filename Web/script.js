let students = [];


// ========================================
// ADD STUDENT
// ========================================

function addStudent() {

    let rollNo = document.getElementById("rollNo").value;
    let name = document.getElementById("studentName").value;

    let java = document.getElementById("java").value;
    let python = document.getElementById("python").value;
    let maths = document.getElementById("maths").value;
    let english = document.getElementById("english").value;
    let dbms = document.getElementById("dbms").value;


    // Check empty fields

    if (
        rollNo === "" ||
        name === "" ||
        java === "" ||
        python === "" ||
        maths === "" ||
        english === "" ||
        dbms === ""
    ) {

        alert("Please enter all student details.");

        return;
    }


    // Check maximum 70 students

    if (students.length >= 70) {

        alert("Maximum 70 students allowed.");

        return;
    }


    // Create data to send to Java

    let studentData =
        "rollNo=" + encodeURIComponent(rollNo)
        + "&name=" + encodeURIComponent(name)
        + "&java=" + encodeURIComponent(java)
        + "&python=" + encodeURIComponent(python)
        + "&maths=" + encodeURIComponent(maths)
        + "&english=" + encodeURIComponent(english)
        + "&dbms=" + encodeURIComponent(dbms);


    // Send data to Java backend

    fetch("/addStudent", {

        method: "POST",

        headers: {
            "Content-Type":
                "application/x-www-form-urlencoded"
        },

        body: studentData

    })

    .then(response => response.json())

    .then(student => {

        // Add Java result to JavaScript array

        students.push(student);


        // Display students

        displayStudents();


        // Update statistics

        updateStatistics();


        // Clear input boxes

        document.getElementById("rollNo").value = "";
        document.getElementById("studentName").value = "";
        document.getElementById("java").value = "";
        document.getElementById("python").value = "";
        document.getElementById("maths").value = "";
        document.getElementById("english").value = "";
        document.getElementById("dbms").value = "";

    })

    .catch(error => {

        console.log(error);

        alert("Could not connect to Java server.");

    });
}


// ========================================
// DISPLAY STUDENTS
// ========================================

function displayStudents() {

    let table =
        document.getElementById("studentTable");

    table.innerHTML = "";


    for (let i = 0; i < students.length; i++) {

        let student = students[i];


        let row = `
            <tr>

                <td>${student.rollNo}</td>

                <td>${student.name}</td>

                <td>${student.total}</td>

                <td>${Number(student.average).toFixed(2)}</td>

                <td>${student.grade}</td>

            </tr>
        `;


        table.innerHTML += row;
    }
}


// ========================================
// CLASS STATISTICS
// ========================================

function updateStatistics() {

    document.getElementById("totalStudents").innerText =
        students.length;


    if (students.length === 0) {

        document.getElementById("classAverage").innerText =
            "0";

        document.getElementById("topper").innerText =
            "None";

        return;
    }


    // Find topper

    let topper = students[0];


    for (let i = 1; i < students.length; i++) {

        if (students[i].total > topper.total) {

            topper = students[i];
        }
    }


    document.getElementById("topper").innerText =
        topper.name;


    // Calculate class average

    let totalMarks = 0;


    for (let i = 0; i < students.length; i++) {

        totalMarks =
            totalMarks + Number(students[i].total);
    }


    let classAverage =
        totalMarks / (students.length * 5);


    document.getElementById("classAverage").innerText =
        classAverage.toFixed(2);
}