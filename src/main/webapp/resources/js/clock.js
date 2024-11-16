function updateClock() {
    const clockElement = document.getElementById('clock');
    const now = new Date();

    const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    };

    clockElement.textContent = now.toLocaleDateString('en-US', options);
}

// Update immediately and then every 8 seconds
updateClock();
setInterval(updateClock, 8000);