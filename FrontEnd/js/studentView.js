document.addEventListener('DOMContentLoaded', () => {
    const studentForm = document.getElementById('studentForm');
    const studentName = document.getElementById('studentName');
    const studentId = document.getElementById('studentId');
    const course = document.getElementById('course');
    const studentsList = document.getElementById('students');

    let selectedStudentId = null;

    document.getElementById('addButton').addEventListener('click', () => {
        const student = {
            name: studentName.value,
            studentId: studentId.value,
            course: course.value
        };

        fetch('http://localhost:8080/api/students', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(student)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            renderStudents();
            studentForm.reset();
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    });

    document.getElementById('updateButton').addEventListener('click', () => {
        if (selectedStudentId == null) return;

        const student = {
            id: selectedStudentId,
            name: studentName.value,
            studentId: studentId.value,
            course: course.value
        };

        fetch(`http://localhost:8080/api/students/${selectedStudentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(student)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            renderStudents();
            studentForm.reset();
            selectedStudentId = null;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    });

    document.getElementById('deleteButton').addEventListener('click', () => {
        if (selectedStudentId == null) return;

        fetch(`http://localhost:8080/api/students/${selectedStudentId}`, {
            method: 'DELETE'
        })
        .then(() => {
            console.log('Student deleted');
            renderStudents();
            studentForm.reset();
            selectedStudentId = null;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    });

    function renderStudents() {
        fetch('http://localhost:8080/api/students')
            .then(response => response.json())
            .then(data => {
                studentsList.innerHTML = '';
                data.forEach(student => {
                    const li = document.createElement('li');
                    li.innerHTML = `
                        <span>${student.name} - ${student.studentId} - ${student.course}</span>
                        <button class="btn select" onclick="selectStudent(${student.id}, '${student.name}', '${student.studentId}', '${student.course}')">Select</button>
                    `;
                    studentsList.appendChild(li);
                });
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    window.selectStudent = function(id, name, studentIdValue, courseValue) {
        selectedStudentId = id;
        studentName.value = name;
        studentId.value = studentIdValue;
        course.value = courseValue;
    };

    renderStudents();
});
