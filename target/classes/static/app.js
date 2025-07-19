document.addEventListener('DOMContentLoaded', () => {
    const photo = document.getElementById('photo');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const uploadForm = document.getElementById('uploadForm');
    const fileInput = document.getElementById('fileInput');

    let currentPhotoId = null;
    let photoHistory = [];

    async function fetchRandomPhoto() {
        try {
            const response = await fetch('/api/photos/random');
            if (response.ok) {
                const blob = await response.blob();
                const url = URL.createObjectURL(blob);
                const contentDisposition = response.headers.get('Content-Disposition');
                const filename = contentDisposition ? contentDisposition.split('filename=')[1] : null;
                const id = filename ? filename.split('.')[0] : null;
                photo.src = url;
                if (id) {
                    currentPhotoId = parseInt(id, 10);
                    photoHistory.push(currentPhotoId);
                }
            } else {
                photo.src = '';
                photo.alt = 'No photos found';
            }
        } catch (error) {
            console.error('Error fetching random photo:', error);
        }
    }

    async function fetchPhotoById(id) {
        try {
            const response = await fetch(`/api/photos/${id}`);
            if (response.ok) {
                const blob = await response.blob();
                const url = URL.createObjectURL(blob);
                photo.src = url;
                currentPhotoId = id;
            } else {
                console.error(`Error fetching photo with id ${id}`);
            }
        } catch (error) {
            console.error('Error fetching photo by id:', error);
        }
    }

    nextBtn.addEventListener('click', async () => {
        await fetchRandomPhoto();
    });

    prevBtn.addEventListener('click', async () => {
        if (photoHistory.length > 1) {
            photoHistory.pop();
            const prevPhotoId = photoHistory[photoHistory.length - 1];
            await fetchPhotoById(prevPhotoId);
        }
    });

    uploadForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('file', fileInput.files[0]);

        try {
            const response = await fetch('/api/photos/upload', {
                method: 'POST',
                body: formData,
            });
            if (response.ok) {
                alert('Photo uploaded successfully');
                await fetchRandomPhoto();
            } else {
                alert('Error uploading photo');
            }
        } catch (error) {
            console.error('Error uploading photo:', error);
        }
    });

    fetchRandomPhoto();
});
