document.addEventListener('DOMContentLoaded', function() {    // Initialize all tooltips with enhanced styling
    try {
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.forEach(function (tooltipTriggerEl) {
            if (bootstrap && bootstrap.Tooltip) {
                new bootstrap.Tooltip(tooltipTriggerEl, {
                    boundary: document.body,
                    animation: true,
                    delay: { show: 100, hide: 100 }
                });
            }
        });
    } catch (e) {
        console.log('Tooltip initialization error: ', e);
    }
    
    // Auto-dismiss alerts after 5 seconds
    setTimeout(function() {
        var alerts = document.querySelectorAll('.alert.alert-success, .alert.alert-info');
        alerts.forEach(function(alert) {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);
    
    // Apply UI enhancements
    highlightActiveNavItem();
    setupFormValidation();
    
    // Initialize UI enhancement functions if they exist
    if (typeof enhanceFormElements === 'function') enhanceFormElements();
    if (typeof enhanceTableInteractions === 'function') enhanceTableInteractions();
    if (typeof styleBadges === 'function') styleBadges();
    if (typeof animateTableRows === 'function') animateTableRows();
    if (typeof initializeActionMenus === 'function') initializeActionMenus();
    
    // Ensure action dropdown menus close when clicking outside
    document.addEventListener('click', function(event) {
        if (!event.target.closest('.action-dropdown')) {
            const openMenus = document.querySelectorAll('.action-dropdown-menu.show');
            openMenus.forEach(menu => menu.classList.remove('show'));
        }
    });
    
    // Handle page mutation for dynamically added elements
    // This ensures our event handlers work even if the DOM is updated
    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            if (mutation.addedNodes.length) {
                if (typeof initializeActionMenus === 'function') initializeActionMenus();
            }
        });
    });
    
    // Start observing the document with the configured parameters
    observer.observe(document.body, { childList: true, subtree: true });
});

// Global function to show delete confirmation modal
function showDeleteModal(empId, empName, deleteUrl) {
    try {
        // Set data in the modal
        document.getElementById('employeeName').textContent = empName;
        document.getElementById('deleteConfirmMessage').textContent = 
            'Are you sure you want to delete ' + empName + '? This action cannot be undone.';
        
        // Set the delete URL to the confirm button
        document.getElementById('confirmDeleteBtn').href = deleteUrl;
        
        // Show the modal using Bootstrap Modal API
        var deleteModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
        deleteModal.show();
    } catch (error) {
        console.error("Error showing modal:", error);
        // Fallback to confirm dialog if modal fails
        if (confirm("Are you sure you want to delete " + empName + "? This action cannot be undone.")) {
            window.location.href = deleteUrl;
        }
    }
}

function confirmDelete(event, message) {
    // Simple confirmation dialog instead of Bootstrap modal to avoid JS errors
    if (confirm(message || 'Are you sure you want to delete this item?')) {
        return true; // Allow the link to be followed
    } else {
        event.preventDefault();
        return false;
    }
}

function highlightActiveNavItem() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    
    navLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (currentPath.startsWith(href)) {
            link.classList.add('active');
            link.setAttribute('aria-current', 'page');
            link.style.color = '#ffffff';
            link.style.fontWeight = '600';
            link.style.backgroundColor = 'rgba(255, 255, 255, 0.1)';
            link.style.borderRadius = '5px';
        }
    });
}

function setupFormValidation() {
    const forms = document.querySelectorAll('.needs-validation');
    
    forms.forEach(form => {
        const inputs = form.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            // Mark required labels
            if (input.hasAttribute('required')) {
                const id = input.getAttribute('id');
                if (id) {
                    const label = document.querySelector(`label[for="${id}"]`);
                    if (label) {
                        label.classList.add('required');
                    }
                }
            }
            
            // Add real-time validation feedback
            input.addEventListener('input', function() {
                let isValid = input.checkValidity();
                
                if (isValid) {
                    input.classList.remove('is-invalid');
                    input.classList.add('is-valid');
                    
                    // Find and remove any existing feedback
                    const container = input.closest('.mb-3') || input.parentElement;
                    const existingFeedback = container.querySelector('.invalid-feedback');
                    if (existingFeedback) {
                        existingFeedback.remove();
                    }
                } else {
                    input.classList.remove('is-valid');
                    input.classList.add('is-invalid');
                    
                    // Add custom feedback message if not already present
                    const container = input.closest('.mb-3') || input.parentElement;
                    if (!container.querySelector('.invalid-feedback')) {
                        const feedback = document.createElement('div');
                        feedback.className = 'invalid-feedback';
                        feedback.textContent = input.validationMessage || 'This field is invalid';
                        container.appendChild(feedback);
                    }
                }
            });
        });
        
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
                
                const invalidInputs = form.querySelectorAll('.is-invalid');
                invalidInputs.forEach(input => {
                    input.style.animation = 'shake 0.5s';
                    setTimeout(() => {
                        input.style.animation = '';
                    }, 500);
                });
            }
            
            form.classList.add('was-validated');
        }, false);
    });
}

function animateTableRows() {
    const tableRows = document.querySelectorAll('tbody tr');
    tableRows.forEach((row, index) => {
        row.style.opacity = '0';
        row.style.animation = `fadeIn 0.3s ease-out forwards ${index * 0.1}s`;
    });
}

// Define a global function that can be called directly from HTML
function toggleActionMenu(buttonElement) {
    // Close all other open menus first
    const allMenus = document.querySelectorAll('.action-dropdown-menu.show');
    allMenus.forEach(menu => {
        if (menu !== buttonElement.nextElementSibling) {
            menu.classList.remove('show');
        }
    });
    
    // Toggle this menu
    const dropdown = buttonElement.nextElementSibling;
    dropdown.classList.toggle('show');
}

(function() {
    const style = document.createElement('style');
    style.textContent = `
        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-5px); }
            50% { transform: translateX(5px); }
            75% { transform: translateX(-5px); }
        }
        
        .card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        
        .btn {
            position: relative;
            overflow: hidden;
            z-index: 1;
        }
        
        .btn::after {
            content: '';
            position: absolute;
            top: 50%;
            left: 50%;
            width: 0;
            height: 0;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 50%;
            transform: translate(-50%, -50%);
            z-index: -1;
            transition: width 0.5s, height 0.5s;
        }
        
        .btn:hover::after {
            width: 300px;
            height: 300px;
        }
    `;
    document.head.appendChild(style);
})();
