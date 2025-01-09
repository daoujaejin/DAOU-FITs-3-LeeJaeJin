# 라이브러리
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# 랜덤 데이터 생성
np.random.seed(42)
# 고객 수
n = 1000
# 고객 특성 생성
data = {
    'customer_id':np.arange(1, n+1),
    'age':np.random.randint(18, 70, size=n),
    'tenure':np.random.randint(1, 10, size=n),
    'balance':np.random.randint(0, 25000, size=n),
    'num_products':np.random.randint(1, 4, size=n),
    'has_credit_card':np.random.choice([0,1], size=n),
    'is_active_member':np.random.choice([0,1], size=n),
    'estimated_salary':np.random.uniform(30000, 150000, size=n),
    'churn':np.random.choice([0,1], size=n, p=[0.8,0.2])
}

# 데이터프레임 생성
df = pd.DataFrame(data)
# CSV 파일로 저장
df.to_csv('customer_churn.csv', index=False)
# 데이터 확인
df.head()
df.info()
