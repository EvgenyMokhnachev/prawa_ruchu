import React from 'react';

interface Props {
  label?: string
  enabled: boolean;
  onChange?: (value: boolean) => void,
  style?: React.CSSProperties
}

export default function Checkbox(props: Props) {

  const handleOnClickOnCheckbox = () => {
    props.onChange && props.onChange(!props.enabled);
  }

  return (
    <div style={{
      display: 'inline-flex',
      alignItems: 'center',
      ...(props.style || {})
    }}>
      <div style={{
        width: '20px',
        height: '20px',
        borderRadius: '2px',
        border: '2px solid black',
        cursor: 'pointer'
      }}
      onClick={handleOnClickOnCheckbox}>
        {props.enabled && (
            <div style={{
              width: '18px',
              height: '18px',
              backgroundColor: '#0F0F0F',
              margin: '1px'
            }}/>
        )}
      </div>

      {props.label && (
          <div style={{
            marginLeft: '10px'
          }}>
            {props.label}
          </div>
      )}
    </div>
  );
}
