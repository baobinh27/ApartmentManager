package com.example.apartmentmanager.tenantapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apartmentmanager.templates.InfoPage
import com.example.apartmentmanager.ui.theme.ApartmentManagerTheme

//Function 4: Báo cáo tài chính
@Composable
fun FinancialReportPage(
    modifier: Modifier,
    onFunctionChange: (Int) -> Unit
) {
    InfoPage(
        title = "Financial Report",
        onBackClick = { onFunctionChange(0) },
        modifier = modifier
    ) {
        Text(text = "Financial Report:")
        Text(text = "Income:")
        Text(text = "Expense:")
        Text(text = "Balance:")
        Text(text = "Other Information:")
        Text(text = "Other Note:")
        Text(text = "Other Last Updated:")
        Text(text = "Financial Report Last Updated:")
    }
}

@Preview(showBackground = true)
@Composable
fun FinancialReportPagePreviewLightMode() {
    ApartmentManagerTheme {
        FinancialReportPage(modifier = Modifier, onFunctionChange = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FinancialReportPagePreviewDarkMode() {
    ApartmentManagerTheme {
        FinancialReportPage(modifier = Modifier, onFunctionChange = {})
    }
}