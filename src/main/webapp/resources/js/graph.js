function drawGraph() {
    const canvas = document.getElementById('graph');
    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;

    // Clear canvas
    ctx.clearRect(0, 0, width, height);

    // Get R value
    const rSelect = document.querySelector('[id$=":r"]');
    const R = rSelect ? parseFloat(rSelect.value) : 2;
    const scale = 70; // Pixels per unit

    // Set styles
    ctx.strokeStyle = '#1a237e';
    ctx.fillStyle = '#1a237e';
    ctx.lineWidth = 2;

    // Draw coordinate system
    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.stroke();

    // Draw arrows
    ctx.beginPath();
    ctx.moveTo(width - 10, centerY - 5);
    ctx.lineTo(width, centerY);
    ctx.lineTo(width - 10, centerY + 5);
    ctx.moveTo(centerX - 5, 10);
    ctx.lineTo(centerX, 0);
    ctx.lineTo(centerX + 5, 10);
    ctx.stroke();

    // Draw shapes with semi-transparent fill
    ctx.fillStyle = 'rgba(26, 35, 126, 0.3)';

    // Rectangle in second quadrant (-R/2, 0), (0, 0), (0, R), (-R/2, R)
    ctx.beginPath();
    ctx.moveTo(centerX - (R / 2 * scale), centerY); // (-R/2, 0)
    ctx.lineTo(centerX, centerY); // (0, 0)
    ctx.lineTo(centerX, centerY - (R * scale)); // (0, R)
    ctx.lineTo(centerX - (R / 2 * scale), centerY - (R * scale)); // (-R/2, R)
    ctx.closePath();
    ctx.fill();

    // Triangle in first quadrant (0, 0), (R/2, 0), (R, 0)
    ctx.beginPath();
    ctx.moveTo(centerX, centerY); // (0, 0)
    ctx.lineTo(centerX + (R / 2 * scale), centerY); // (R/2, 0)
    ctx.lineTo(centerX, centerY - (R * scale)); // (R, 0)
    ctx.closePath();
    ctx.fill();

    // Quarter circle in third quadrant
    ctx.beginPath();
    ctx.arc(centerX, centerY, R / 2 * scale, Math.PI / 2, Math.PI, false);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    // Draw axis labels
    ctx.fillStyle = '#1a237e';
    ctx.font = '12px Arial';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';

    // X axis labels
    ctx.fillText('R', centerX + (R * scale), centerY + 20);
    ctx.fillText('R/2', centerX + (R / 2 * scale), centerY + 20);
    ctx.fillText('-R/2', centerX - (R / 2 * scale), centerY + 20);
    ctx.fillText('-R', centerX - (R * scale), centerY + 20);

    // Y axis labels
    ctx.fillText('R', centerX - 20, centerY - (R * scale));
    ctx.fillText('R/2', centerX - 20, centerY - (R / 2 * scale));
    ctx.fillText('-R/2', centerX - 20, centerY + (R / 2 * scale));
    ctx.fillText('-R', centerX - 20, centerY + (R * scale));
}

// Initial draw
drawGraph();

// Handle canvas clicks
document.getElementById('graph').addEventListener('click', function (event) {
    const canvas = event.target;
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const scale = 70;

    const rSelect = document.querySelector('[id$=":r"]');
    if (!rSelect || !rSelect.value) {
        alert('Please select R value first');
        return;
    }
    const R = parseFloat(rSelect.value);

    // Convert coordinates
    let xCoord = (x - centerX) / scale * R;
    let yCoord = (centerY - y) / scale * R;

    // Find closest valid X value
    const validXValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
    const closestX = validXValues.reduce((prev, curr) => {
        return (Math.abs(curr - xCoord) < Math.abs(prev - xCoord) ? curr : prev);
    });

    // Round Y to 2 decimal places
    yCoord = Math.round(yCoord * 100) / 100;

    // Validate Y
    if (yCoord < -3 || yCoord > 5) {
        alert('Y value must be between -3 and 5');
        return;
    }

    // Update form inputs
    const xSelect = document.querySelector('[id$=":x"]');
    const yInput = document.querySelector('[id$=":y"]');

    if (xSelect && yInput) {
        xSelect.value = closestX;
        yInput.value = yCoord;

        // Submit form
        const submitButton = document.querySelector('[id$=":check"]');
        if (submitButton) {
            submitButton.click();
        }
    }
});