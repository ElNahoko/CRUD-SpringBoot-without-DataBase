/**
 * Dashboard functionality for Employee Management System
 */

document.addEventListener('DOMContentLoaded', function() {
    // Enhance chart tooltips
    if (typeof Chart !== 'undefined') {
        // Set global defaults for all charts
        Chart.defaults.font.family = "'Poppins', sans-serif";
        Chart.defaults.color = '#64748b';
        Chart.defaults.plugins.tooltip.backgroundColor = 'rgba(45, 55, 72, 0.9)';
        Chart.defaults.plugins.tooltip.titleColor = '#ffffff';
        Chart.defaults.plugins.tooltip.bodyColor = '#f8faff';
        Chart.defaults.plugins.tooltip.padding = 10;
        Chart.defaults.plugins.tooltip.cornerRadius = 6;
        Chart.defaults.plugins.tooltip.displayColors = false;
        
        // Custom tooltip formatter
        Chart.defaults.plugins.tooltip.callbacks = {
            label: function(context) {
                return context.label + ': ' + context.raw + ' employees';
            }
        };
        
        // Add subtle animation
        Chart.defaults.animation = {
            duration: 1500,
            easing: 'easeOutQuart'
        };
    }
    
    // Add hover effect to stat cards
    const statCards = document.querySelectorAll('.stat-card');
    statCards.forEach(card => {
        card.addEventListener('mouseover', function() {
            // Create pulse effect on icon
            const icon = this.querySelector('.stat-icon');
            if (icon) {
                icon.style.transform = 'scale(1.1)';
                icon.style.transition = 'transform 0.3s ease';
            }
        });
        
        card.addEventListener('mouseout', function() {
            const icon = this.querySelector('.stat-icon');
            if (icon) {
                icon.style.transform = 'scale(1)';
            }
        });
    });
    
    // Make chart responsive
    let charts = [];
    if (typeof Chart !== 'undefined' && document.getElementById('departmentChart')) {
        // Store chart references
        const deptChart = Chart.getChart('departmentChart');
        const salaryChart = Chart.getChart('salaryChart');
        const positionChart = Chart.getChart('positionChart');
        
        if (deptChart) charts.push(deptChart);
        if (salaryChart) charts.push(salaryChart);
        if (positionChart) charts.push(positionChart);
        
        // Make charts responsive on window resize
        window.addEventListener('resize', function() {
            charts.forEach(chart => {
                if (chart) chart.resize();
            });
        });
    }
    
    // Make form cards same height in each row
    function adjustCardHeights() {
        const rows = document.querySelectorAll('.row');
        rows.forEach(row => {
            const cards = row.querySelectorAll('.form-card');
            if (cards.length > 1) {
                let maxHeight = 0;
                // Reset heights first
                cards.forEach(card => {
                    card.style.height = 'auto';
                    const cardHeight = card.offsetHeight;
                    maxHeight = Math.max(maxHeight, cardHeight);
                });
                
                // Apply max height to all cards in the row
                cards.forEach(card => {
                    card.style.height = maxHeight + 'px';
                });
            }
        });
    }
    
    // Run on load and on resize
    adjustCardHeights();
    window.addEventListener('resize', adjustCardHeights);
});
