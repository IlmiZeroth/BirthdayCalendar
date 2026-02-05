const toggleBtn = document.getElementById('toggleFormBtn');
const form = document.getElementById('addBirthdayForm');
const cancelBtn = document.getElementById('cancelBtn');

const nameInput = form.querySelector('input[name="name"]');
const dateInput = form.querySelector('input[name="date"]');
const photoInput = form.querySelector('input[name="photo"]');
const descInput = form.querySelector('textarea[name="description"]');
const idInput = form.querySelector('input[name="id"]');

const allBirthdaysBtn = document.getElementById('allBirthdays');
const nearBirthdaysBtn = document.getElementById('nearestBirthdays');

const allBirthdaysTab = document.getElementById('allBirthdaysTab');
const nearBirthdaysTab = document.getElementById('nearBirthdaysTab');



function resetForm() {
    form.reset();
    let list = document.getElementById('allBirthdaysList');
    if (allBirthdaysTab.style.display === 'none')
        list = document.getElementById('nearBirthdaysList');
    list.appendChild(form);
    idInput.value = '';
    form.dataset.mode = 'add';
    form.method = 'post'
    form.action = '/birthdays/add';
}

allBirthdaysBtn.addEventListener('click', () => {
    allBirthdaysTab.style.display = 'block';
    nearBirthdaysTab.style.display = 'none';
    allBirthdaysBtn.classList.add('active');
    nearBirthdaysBtn.classList.remove('active');
});

nearBirthdaysBtn.addEventListener('click', () => {
    allBirthdaysTab.style.display = 'none';
    nearBirthdaysTab.style.display = 'block';
    nearBirthdaysBtn.classList.add('active');
    allBirthdaysBtn.classList.remove('active');
});


toggleBtn.addEventListener('click', () => {
    resetForm();
    form.style.display = 'block';
});


cancelBtn.addEventListener('click', () => {
    resetForm();
    form.style.display = 'none';
});

document.addEventListener('click', (e) => {
    const editBtn = e.target.closest('.edit-btn');
    if (!editBtn) return;

    const card = editBtn.closest('.birthday-card');

    const nameElement = card.querySelector('.birthday-name');
    const dateElement = card.querySelector('.birthday-date');
    const photoElement = card.querySelector('.birthday-image');
    const descElement = card.querySelector('.birthday-description');
    const idElement = card.querySelector('.birthday-id');

    nameInput.value = nameElement ? nameElement.textContent : '';
    dateInput.value = dateElement ? dateElement.textContent : '';
    photoInput.value = photoElement ? photoElement.src : '';
    descInput.value = descElement ? descElement.textContent : '';
    idInput.value = idElement ? idElement.textContent : '';


    form.dataset.mode = 'edit';
    form.action = '/birthdays/update';
    form.style.display = 'block';

    card.after(form);
});