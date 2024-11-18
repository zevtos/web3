function validateForm() {
    const xSelect = document.querySelector('[id$=":x"]');
    const yInput = document.querySelector('[id$=":y"]');
    const rSelect = document.querySelector('[id$=":r"]');

    // Reset previous error states
    clearErrors();

    let isValid = true;

    // Validate X
    if (!xSelect?.value) {
        showError(xSelect, 'X value is required');
        isValid = false;
    }

    // Validate Y
    if (yInput) {
        const yValue = yInput.value.trim();

        // Check if empty
        if (!yValue) {
            showError(yInput, 'Y value is required');
            isValid = false;
        }
        // Check if it's a valid number
        else if (!/^-?\d*\.?\d+$/.test(yValue)) {
            showError(yInput, 'Y must be a valid number and use a dot as a decimal separator');
            isValid = false;
        }
        // Check range
        else {
            const y = parseFloat(yValue);
            if (y < -3 || y > 5) {
                showError(yInput, 'Y must be between -3 and 5');
                isValid = false;
            }
        }
    }

    // Validate R
    if (!rSelect?.value) {
        showError(rSelect, 'R value is required');
        isValid = false;
    }

    return isValid;
}

function showError(element, message) {
    // Add error class to parent container
    element.parentElement?.classList.add('error');

    // Create or update error message
    let errorDiv = element.parentElement?.querySelector('.error-message');
    if (!errorDiv) {
        errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        element.parentElement?.appendChild(errorDiv);
    }
    errorDiv.textContent = message;

    // Highlight the input
    element.classList.add('input-error');
}

function clearErrors() {
    // Remove all error messages and classes
    document.querySelectorAll('.error-message').forEach(el => el.remove());
    document.querySelectorAll('.input-error').forEach(el => el.classList.remove('input-error'));
    document.querySelectorAll('.error').forEach(el => el.classList.remove('error'));
}

// Add form validation
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');

    // Validate on submit
    form?.addEventListener('submit', function(event) {
        if (!validateForm()) {
            event.preventDefault();
            event.stopPropagation();
            return false;
        }
        return true;
    });

    // Real-time validation for Y input
    const yInput = document.querySelector('[id$=":y"]');
    yInput?.addEventListener('input', function() {
        validateYInput(this);
    });
});

function validateYInput(input) {
    const value = input.value.trim();

    // Allow empty value (will be caught on submit)
    if (!value) return;

    // Check if it's a valid number
    if (!/^-?\d*\.?\d+$/.test(value)) {
        showError(input, 'Y must be a valid number');
        return;
    }

    const y = parseFloat(value);
    if (y < -3 || y > 5) {
        showError(input, 'Y must be between -3 and 5');
    } else {
        clearErrors();
    }
}

function drawGraph() {
    const canvas = document.getElementById('graph');
    if (!canvas) {
        console.error('Canvas element not found');
        return;
    }

    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;

    // Clear canvas
    ctx.clearRect(0, 0, width, height);

    // Get R value with validation
    const rSelect = document.querySelector('[id$=":r"]');
    const R = rSelect && !isNaN(parseFloat(rSelect.value)) ? parseFloat(rSelect.value) : 2;

    // Adjust scale based on R value to ensure graph fits
    const scale = Math.min(width, height) / (3 * R); // Динамический масштаб

    // Enhanced styling
    ctx.strokeStyle = 'rgba(44, 62, 80, 0.8)';
    ctx.fillStyle = 'rgba(44, 62, 80, 0.8)';
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

    // Draw shapes
    ctx.fillStyle = 'rgba(52, 152, 219, 0.3)';

    // Rectangle in second quadrant
    ctx.beginPath();
    ctx.moveTo(centerX - (R/2 * scale), centerY);
    ctx.lineTo(centerX, centerY);
    ctx.lineTo(centerX, centerY - (R * scale));
    ctx.lineTo(centerX - (R/2 * scale), centerY - (R * scale));
    ctx.closePath();
    ctx.fill();

    // Triangle in first quadrant
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + (R/2 * scale), centerY);
    ctx.lineTo(centerX, centerY - (R * scale));
    ctx.closePath();
    ctx.fill();

    // Quarter circle in third quadrant
    ctx.beginPath();
    ctx.arc(centerX, centerY, R/2 * scale, Math.PI/2, Math.PI, false);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    // Draw axis labels
    ctx.fillStyle = 'rgba(44, 62, 80, 0.9)';
    ctx.font = '13px "Segoe UI", sans-serif';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';

    // X axis labels
    ctx.fillText('R', centerX + (R * scale), centerY + 20);
    ctx.fillText('R/2', centerX + (R/2 * scale), centerY + 20);
    ctx.fillText('-R/2', centerX - (R/2 * scale), centerY + 20);
    ctx.fillText('-R', centerX - (R * scale), centerY + 20);

    // Y axis labels
    ctx.fillText('R', centerX - 20, centerY - (R * scale));
    ctx.fillText('R/2', centerX - 20, centerY - (R/2 * scale));
    ctx.fillText('-R/2', centerX - 20, centerY + (R/2 * scale));
    ctx.fillText('-R', centerX - 20, centerY + (R * scale));

    // Draw all points from the table
    drawAllPoints(ctx, centerX, centerY, scale, R);
}

function drawPoint(ctx, x, y, color) {
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.arc(x, y, 4, 0, 2 * Math.PI);
    ctx.fill();
}

function drawAllPoints(ctx, centerX, centerY, scale, currentR) {
    const table = document.querySelector('.data-table');
    if (!table) return;

    const rows = table.getElementsByTagName('tr');
    for (let i = 1; i < rows.length; i++) { // Skip header row
        const cells = rows[i].getElementsByTagName('td');
        if (cells.length >= 4) {
            const x = parseFloat(cells[0].textContent);
            const y = parseFloat(cells[1].textContent);
            const r = parseFloat(cells[2].textContent);
            if (r !== currentR) continue;
            const hit = cells[3].textContent.trim().toLowerCase() === 'yes';

            // Scale coordinates relative to current R
            const scaledX = centerX + (x / currentR * (currentR * scale));
            const scaledY = centerY - (y / currentR * (currentR * scale));

            drawPoint(ctx, scaledX, scaledY, hit ? '#2ecc71' : '#e74c3c');
        }
    }
}

// Initial draw with error handling
try {
    drawGraph();
} catch (error) {
    console.error('Error during initial graph drawing:', error);
}

// Handle canvas clicks
document.getElementById('graph')?.addEventListener('click', function(event) {
    try {
        const canvas = event.target;
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;

        const rSelect = document.querySelector('[id$=":r"]');
        if (!rSelect || !rSelect.value) {
            alert('Please select R value first');
            return;
        }
        const R = parseFloat(rSelect.value);

        // Use dynamic scale
        const scale = Math.min(canvas.width, canvas.height) / (3 * R);

        // Convert coordinates (fixed to prevent doubling)
        let xCoord = ((x - centerX) / scale) * (R / R);
        let yCoord = ((centerY - y) / scale) * (R / R);

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
    } catch (error) {
        console.error('Error handling canvas click:', error);
    }
});

// Add form validation before submit
document.querySelector('form')?.addEventListener('submit', function(event) {
    if (!validateForm()) {
        event.preventDefault();
    }
});

// Redraw graph when R value changes
document.querySelector('[id$=":r"]')?.addEventListener('change', drawGraph);