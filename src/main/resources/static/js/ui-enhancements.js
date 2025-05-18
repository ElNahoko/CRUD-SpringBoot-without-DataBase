/**
 * UI Enhancements for Employee Management System
 */

function enhanceFormElements() {
    // Group form fields into logical sections with visual grouping
    const formGroups = document.querySelectorAll('.card-body');
    formGroups.forEach(group => {
        const headings = group.querySelectorAll('h5, h6');
        headings.forEach(heading => {
            // Find the next set of form fields until the next heading
            let currentElement = heading.nextElementSibling;
            const fieldGroup = document.createElement('div');
            fieldGroup.className = 'form-field-group';
            
            // Create a title for the field group
            const title = document.createElement('div');
            title.className = 'form-field-group-title';
            
            // Add an appropriate icon based on the heading text
            const icon = document.createElement('i');
            const headingText = heading.textContent.toLowerCase();
            
            if (headingText.includes('basic') || headingText.includes('personal')) {
                icon.className = 'bi bi-person-vcard';
            } else if (headingText.includes('contact')) {
                icon.className = 'bi bi-envelope';
            } else if (headingText.includes('employment')) {
                icon.className = 'bi bi-briefcase';
            } else if (headingText.includes('address') || headingText.includes('location')) {
                icon.className = 'bi bi-geo-alt';
            } else if (headingText.includes('additional')) {
                icon.className = 'bi bi-info-circle';
            } else {
                icon.className = 'bi bi-card-list';
            }
            
            title.appendChild(icon);
            title.appendChild(document.createTextNode(' ' + heading.textContent));
            fieldGroup.appendChild(title);
            
            // Replace the original heading with our new group
            heading.parentNode.insertBefore(fieldGroup, heading);
            heading.style.display = 'none';
            
            // Move form fields into the group until next heading
            while (currentElement && !['H5', 'H6'].includes(currentElement.tagName)) {
                const nextElement = currentElement.nextElementSibling;
                fieldGroup.appendChild(currentElement);
                currentElement = nextElement;
            }
        });
    });
    
    // Enhance number input fields with currency indicators
    const salaryInputs = document.querySelectorAll('input[id*="Sal"], input[id*="salary"]');
    salaryInputs.forEach(input => {
        if (!input.closest('.input-group')) {
            const wrapper = document.createElement('div');
            wrapper.className = 'input-group';
            
            // Create currency symbol addon
            const currencySymbol = document.createElement('span');
            currencySymbol.className = 'input-group-text';
            currencySymbol.textContent = 'MAD';
            
            // Replace the input with our new input group
            input.parentNode.insertBefore(wrapper, input);
            wrapper.appendChild(input);
            wrapper.appendChild(currencySymbol);
        }
    });
    
    // Enhance mobile inputs with country code
    const mobileInputs = document.querySelectorAll('input[id*="Mobile"], input[type="tel"]');
    mobileInputs.forEach(input => {
        if (!input.closest('.input-group')) {
            const wrapper = document.createElement('div');
            wrapper.className = 'input-group';
            
            // Create country code addon
            const countryCode = document.createElement('span');
            countryCode.className = 'input-group-text';
            countryCode.textContent = '+212';
            
            // Replace the input with our new input group
            input.parentNode.insertBefore(wrapper, input);
            wrapper.appendChild(countryCode);
            wrapper.appendChild(input);
        }
    });
    
    // Enhance form controls with icons based on their type
    const formLabels = document.querySelectorAll('.form-label');
    formLabels.forEach(label => {
        const labelText = label.textContent.toLowerCase();
        const input = document.querySelector(`#${label.getAttribute('for')}`);
        
        if (!input) return;
        
        const icon = document.createElement('i');
        icon.className = 'bi';
        
        // Determine appropriate icon based on input type/name
        if (labelText.includes('name')) {
            icon.classList.add('bi-person');
        } else if (labelText.includes('mobile') || labelText.includes('phone')) {
            icon.classList.add('bi-telephone');
        } else if (labelText.includes('email')) {
            icon.classList.add('bi-envelope');
        } else if (labelText.includes('salary')) {
            icon.classList.add('bi-currency-exchange');
        } else if (labelText.includes('department')) {
            icon.classList.add('bi-diagram-3');
        } else if (labelText.includes('position')) {
            icon.classList.add('bi-briefcase');
        } else if (labelText.includes('hire') || labelText.includes('date')) {
            icon.classList.add('bi-calendar');
        } else if (labelText.includes('address')) {
            icon.classList.add('bi-geo-alt');
        } else if (labelText.includes('status') || labelText.includes('active')) {
            icon.classList.add('bi-toggle-on');
        } else {
            icon.classList.add('bi-chevron-right');
        }
        
        // Add icon to label
        if (!label.querySelector('.bi')) {
            label.prepend(icon);
            icon.style.marginRight = '0.5rem';
        }
    });
}

function enhanceTableInteractions() {
    // Add row hover effects
    const tableRows = document.querySelectorAll('table tbody tr');
    tableRows.forEach(row => {
        row.addEventListener('mouseenter', () => {
            row.style.backgroundColor = 'rgba(67, 97, 238, 0.05)';
            row.style.transform = 'scale(1.01)';
            row.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
            row.style.zIndex = '1';
        });
        
        row.addEventListener('mouseleave', () => {
            row.style.backgroundColor = '';
            row.style.transform = '';
            row.style.boxShadow = '';
            row.style.zIndex = '';
        });
    });
    
    // Add visual feedback to action buttons
    const actionButtons = document.querySelectorAll('.btn-outline-info, .btn-outline-warning, .btn-outline-danger');
    actionButtons.forEach(button => {
        button.addEventListener('mouseenter', () => {
            const icon = button.querySelector('i');
            if (icon) {
                if (button.classList.contains('btn-outline-info')) {
                    icon.style.transform = 'scale(1.2)';
                } else if (button.classList.contains('btn-outline-warning')) {
                    icon.style.transform = 'rotate(15deg)';
                } else if (button.classList.contains('btn-outline-danger')) {
                    icon.style.animation = 'shake 0.5s';
                }
            }
        });
        
        button.addEventListener('mouseleave', () => {
            const icon = button.querySelector('i');
            if (icon) {
                icon.style.transform = '';
                icon.style.animation = '';
            }
        });
    });
}

function styleBadges() {
    // Apply styling to department badges
    const departmentBadges = document.querySelectorAll('.badge.bg-info');
    departmentBadges.forEach(badge => {
        const deptName = badge.textContent.trim();
        badge.setAttribute('data-department', deptName);
        badge.classList.add('dept-' + deptName.replace(/\s+/g, ''));
    });
    
    // Apply styling to status badges
    const statusBadges = document.querySelectorAll('.badge.bg-success, .badge.bg-secondary');
    statusBadges.forEach(badge => {
        badge.classList.add('status-badge');
        const isActive = badge.textContent.trim() === 'Active';
        
        const indicator = document.createElement('span');
        indicator.className = 'status-indicator ' + (isActive ? 'status-active' : 'status-inactive');
        badge.prepend(indicator);
    });
    
    // Apply styling to salary display
    const salaryElements = document.querySelectorAll('.badge.bg-success-subtle');
    salaryElements.forEach(element => {
        element.classList.add('salary-badge');
    });
}

// Register these functions to run on page load if not already
document.addEventListener('DOMContentLoaded', function() {
    if (typeof window.enhancementsLoaded === 'undefined') {
        enhanceFormElements();
        enhanceTableInteractions();
        styleBadges();
        window.enhancementsLoaded = true;
    }
});

/**
 * Initialize action menu event listeners
 * This is needed for dynamically added elements
 */
function initializeActionMenus() {
    // Find all action buttons without event listeners and add them
    const actionButtons = document.querySelectorAll('.toggle-action-menu:not([data-initialized])');
    actionButtons.forEach(button => {
        button.setAttribute('data-initialized', 'true');
        button.addEventListener('click', function(e) {
            e.stopPropagation();
        });
    });
}

// Add event listener to document to initialize action menus
document.addEventListener('DOMContentLoaded', function() {
    // Initialize all action menus
    initializeActionMenus();
});

/**
 * Apply counter animation to statistics numbers
 * This function creates a counting animation for statistic numbers
 */
function animateStatCounters() {
    const statValues = document.querySelectorAll('.stat-value');
    
    statValues.forEach(stat => {
        // Skip if already animated
        if (stat.classList.contains('animated')) return;
        
        // Get the end number
        const text = stat.textContent;
        let endNum = parseFloat(text.replace(/[^0-9.-]+/g, ''));
        let suffix = text.replace(/[0-9.-]+/g, '');
        
        if (isNaN(endNum)) return;
        
        // Mark as animated
        stat.classList.add('animated');
        
        // Start from zero
        let startNum = 0;
        let duration = 1000;
        let startTime = null;
        
        // Animation function
        function animate(timestamp) {
            if (!startTime) startTime = timestamp;
            const progress = Math.min((timestamp - startTime) / duration, 1);
            
            // Easing function for smooth animation
            const easeOutQuad = t => t * (2 - t);
            const easedProgress = easeOutQuad(progress);
            
            // Calculate current number
            const currentNum = Math.floor(startNum + easedProgress * (endNum - startNum));
            
            // Update the text
            if (Number.isInteger(endNum)) {
                stat.textContent = currentNum + suffix;
            } else {
                // For decimal numbers, use 2 decimal places
                stat.textContent = currentNum.toFixed(2) + suffix;
            }
            
            if (progress < 1) {
                requestAnimationFrame(animate);
            } else {
                // Ensure final number is exactly the target
                stat.textContent = text;
            }
        }
        
        // Start the animation
        requestAnimationFrame(animate);
    });
}

// Add event listener to run when DOM is fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // If we're on the dashboard page, run the counter animation
    if (window.location.pathname.includes('dashboard')) {
        setTimeout(animateStatCounters, 400); // Slight delay to let the page render
    }
});
