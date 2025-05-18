/**
 * Employee deletion handler script
 * This script handles the delete modal functionality for the employee management system
 */

// Document ready function to ensure DOM is fully loaded
document.addEventListener("DOMContentLoaded", function() {
    console.log("Delete handler initialized");
    
    // Initialize Bootstrap modals
    var deleteConfirmModal = document.getElementById('deleteConfirmModal');
    if (deleteConfirmModal) {
        // Add event listener when modal is shown
        deleteConfirmModal.addEventListener('shown.bs.modal', function() {
            console.log("Modal shown, checking for delete form");
            
            // Check for delete form after modal is shown
            const deleteForm = document.getElementById('deleteForm');
            if (deleteForm) {
                console.log("Delete form found in modal");
            } else {
                console.error("Delete form still not found after modal is shown");
            }
        });
    }
    
    // Check for delete form in the initial DOM
    const deleteForm = document.getElementById('deleteForm');
    if (deleteForm) {
        console.log("Delete form found, adding event listener");
        deleteForm.addEventListener('submit', function(event) {
            const action = this.getAttribute('action');
            console.log("Form submitting to:", action);
            
            // Validate the action URL before submission
            if (!action || action === '') {
                console.error("Form action is empty, preventing submission");
                event.preventDefault();
                alert("Error: Cannot delete employee. Please try again.");
            }
        });
    } else {
        console.log("Delete form not found on initial page load");
    }
});

// Global function to show delete modal
function showDeleteModal(element) {
    try {
        // Get data from data attributes
        const empId = element.getAttribute('data-emp-id');
        const empName = element.getAttribute('data-emp-name');
        const deleteUrl = element.getAttribute('data-delete-url');
        
        console.log("ShowDeleteModal called with:", {empId, empName, deleteUrl});
        
        // Set employee name in the modal
        var nameElement = document.getElementById('employeeName');
        if (nameElement) {
            nameElement.textContent = empName;
        }
        
        // Set confirmation message with employee name
        var messageElement = document.getElementById('deleteConfirmMessage');
        if (messageElement) {
            messageElement.textContent = 'Are you sure you want to delete ' + empName + '? This action cannot be undone.';
        }
        
        // Set the form action before showing the modal
        var deleteForm = document.getElementById('deleteForm');
        if (deleteForm) {
            if (deleteUrl) {
                deleteForm.action = deleteUrl;
                console.log("Delete form action set to:", deleteUrl);
            } else {
                console.error("Delete URL is empty or null");
            }
        } else {
            console.error("Delete form not found");
        }
        
        // Show the modal using Bootstrap Modal API
        var modalElement = document.getElementById('deleteConfirmModal');
        if (modalElement) {
            var deleteModal = new bootstrap.Modal(modalElement);
            deleteModal.show();
        }} catch (error) {
        console.error("Error showing modal:", error);
        // Get employee name directly if possible
        const empName = element.getAttribute('data-emp-name') || 'this employee';
        const deleteUrl = element.getAttribute('data-delete-url');
        
        // Fallback to confirm dialog if modal fails
        if (deleteUrl && confirm("Are you sure you want to delete " + empName + "? This action cannot be undone.")) {
            window.location.href = deleteUrl;
        } else {
            alert("Error: Unable to process delete request. Please try again.");
        }
    }
}

// Add event listener to handle modal close and cleanup
document.addEventListener('DOMContentLoaded', function() {
    var deleteConfirmModal = document.getElementById('deleteConfirmModal');
    if (deleteConfirmModal) {
        deleteConfirmModal.addEventListener('hidden.bs.modal', function() {
            // Reset form action when modal is hidden
            var deleteForm = document.getElementById('deleteForm');
            if (deleteForm) {
                deleteForm.action = '';
            }
        });
    }
});
