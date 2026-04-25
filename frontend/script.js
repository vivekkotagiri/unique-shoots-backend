// 1. Navbar Scroll Effect
window.addEventListener('scroll', function() {
    const navbar = document.getElementById('mainNav');
    if (window.scrollY > 50) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

// 2. Package Selection Logic
function selectPackage(packageName, price) {
    // Remove the 'selected' class from all cards
    const cards = document.querySelectorAll('.pricing-card');
    cards.forEach(card => card.classList.remove('selected'));

    // Reset button texts
    const buttons = document.querySelectorAll('.btn-select');
    buttons.forEach(btn => btn.innerText = 'Select Package');

    // Add 'selected' class to the clicked card based on ID
    const selectedCard = document.getElementById(packageName === 'Starter' ? 'pkg-starter' : 'pkg-extended');
    selectedCard.classList.add('selected');
    
    // Change button text of selected card
    selectedCard.querySelector('.btn-select').innerText = 'Selected ✓';

    // Update hidden form input and alert box
    document.getElementById('selectedPackageName').value = packageName + " (₹" + price + ")";
    
    const alertBox = document.getElementById('selected-package-alert');
    alertBox.classList.remove('d-none');
    document.getElementById('display-package').innerText = packageName + " Package (₹" + price + ")";
    
    // Smooth scroll to the booking form
    document.getElementById('book').scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// 3. Form Submission Handler (Prepares data for Spring Boot)
function handleFormSubmit(event) {
    event.preventDefault(); // Prevents the page from refreshing

    // Check if a package was selected
    const selectedPackage = document.getElementById('selectedPackageName').value;
    if (!selectedPackage) {
        alert("Please select a package (Starter or Extended) before submitting!");
        // Scroll back up to pricing
        document.getElementById('pricing').scrollIntoView({ behavior: 'smooth' });
        return;
    }

    // Gather all form data
    const bookingData = {
        name: document.getElementById('clientName').value,
        phone: document.getElementById('clientPhone').value,
        eventType: document.getElementById('eventType').value,
        date: document.getElementById('eventDate').value,
        address: document.getElementById('eventAddress').value,
        package: selectedPackage
    };

    // Change button text to show it's working
    const submitBtn = document.querySelector('.submit-btn');
    const originalText = submitBtn.innerText;
    submitBtn.innerText = "Processing Booking...";
    submitBtn.disabled = true;

    // Send the real data to your Spring Boot Backend
    fetch('http://localhost:8080/api/bookings/submit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        // We make sure the variable names match the Java Booking.java file EXACTLY
        body: JSON.stringify({
            clientName: bookingData.name,
            clientPhone: bookingData.phone,
            eventType: bookingData.eventType,
            eventDate: bookingData.date,
            eventAddress: bookingData.address,
            selectedPackage: bookingData.package
        })
    })
    .then(response => {
        if (response.ok) {
            alert("Success! Your booking request has been recorded. We will WhatsApp you shortly.");
            // Reset form
            document.getElementById('bookingForm').reset();
            const cards = document.querySelectorAll('.pricing-card');
            cards.forEach(card => card.classList.remove('selected'));
            document.getElementById('selected-package-alert').classList.add('d-none');
            document.getElementById('selectedPackageName').value = "";
        } else {
            alert("Oops! Something went wrong. Please try again.");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("Server is currently offline. Please contact us directly on Instagram.");
    })
    .finally(() => {
        submitBtn.innerText = originalText;
        submitBtn.disabled = false;
    });
}